package com.yueqiu.framework.config;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
public class MybatisConfig {

    @Autowired
    private Environment environment;

    /**
     * sqlSession工厂
     * @return
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        //读取配置文件中domain的路径
        String typeAliasesPackage = environment.getProperty("mybatis.type-aliases-package");
        String mapperLocations = environment.getProperty("mybatis.mapper-locations");
        String configLocation = environment.getProperty("mybatis.config-location");
        final SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        VFS.addImplClass(SpringBootVFS.class);

        //获取详细的domain路径
        typeAliasesPackage = setTypeAliasesPackage(typeAliasesPackage);
        factory.setTypeAliasesPackage(typeAliasesPackage);
        factory.setMapperLocations(resolveMapperLocations(StringUtils.split(mapperLocations,",")));
        factory.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
        return factory.getObject();
    }



    private Resource[] resolveMapperLocations(String[] mapperLocations) throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<Resource> resourceList =  new ArrayList<>();
        if(mapperLocations.length>0){
            for(String srt : mapperLocations){
                try {
                    Resource[] resources = resolver.getResources(srt);
                    resourceList.addAll(Arrays.asList(resources));
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }

        }

        return resourceList.toArray(new Resource[resourceList.size()]);
    }


    private String setTypeAliasesPackage(String typeAliasesPackage) {
        //资源路径分解器,用于解析带有*等通配符路径的资源文件
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //元数据读取器：读取类名
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);

        List<String> typeAliasesPackagePaths = new ArrayList<>();

        try{
            for(String strPath : typeAliasesPackage.split(",")){
                List<String> result = new ArrayList<>();
                strPath = resolver.CLASSPATH_ALL_URL_PREFIX +
                        ClassUtils.convertClassNameToResourcePath(strPath.trim()) +
                        "/**/*.class";
                Resource[] resources = resolver.getResources(strPath);
                for (Resource resource : resources){
                    MetadataReader metadataReader = null;
                    try {
                        metadataReader = metadataReaderFactory.getMetadataReader(resource);
                        result.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName());

                    }
                    catch (Throwable e){
                        e.printStackTrace();
                    }

                }
                if(result.size()>0){
                    HashSet<String> hashSet = new HashSet<>(result);
                    typeAliasesPackagePaths.addAll(hashSet);
                }
            }
            if(typeAliasesPackagePaths.size()>0){
                String[] strs = (String[]) typeAliasesPackagePaths.toArray(new String[0]);
                typeAliasesPackage = String.join(",",strs);
            }
            else {
                throw new RuntimeException("typeAliasesPackage找不到！");
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return typeAliasesPackage;

    }
}
