package com.chen.tools.platform.frame.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public final class PropertiesUtils { 

    private static Properties properties = new Properties();

    // 默认的配置文件存放路径
    private static String dDEFAULTPROPPATH = "/opt/netwatcher/pm4h2/app/conf";

    // 存放default_prop_path。properties或者jvm参数中的key值
    private static String dDEFAULTPROPPATHKEY = "DEFAULT_PROP_PATH";

    private static final String BASEPATH = "pm4h2";

    private static final String RELATIVEPATH = "pm4h2/app/conf";

    private PropertiesUtils()
    {
    }

    /**
     * 
     * 从目录propertiesFilePath中载入所有配置文件的配置项到properties中
     * 
     */
    public static void loadProperties()
    {
        new PropertiesUtils().readPropPath();
        File foldersPt = new File(dDEFAULTPROPPATH);
        if (!foldersPt.exists())
        {
            return;
        }
        InputStream fisPtlP = null;

        if (foldersPt.isDirectory())
        {
            File[] confPtFiles = foldersPt.listFiles();

            // 将目录中的所有配置文件中的配置项均读入到properties对象中
            // 如果配置项存在重复key，则后加载的将覆盖先加载的，应避免重复key的出现
            for (File filesPt : confPtFiles)
            {
                if (filesPt.isDirectory())
                {
                    continue;
                }
                // 只读properties文件
                if (!filesPt.getName().endsWith(".properties"))
                {
                    continue;
                }
                try
                {
                    fisPtlP = new FileInputStream(filesPt);
                    properties.load(fisPtlP);
                }
                catch (FileNotFoundException e_lP)
                {
                    new PropertiesUtils().doNothing();
                }
                catch (IOException e_lP)
                {
                    new PropertiesUtils().doNothing();
                }
                finally
                {
                    if (fisPtlP != null)
                    {
                        try
                        {
                            fisPtlP.close();
                        }
                        catch (IOException ex)
                        {
                            new PropertiesUtils().doNothing();
                        }
                    }
                }
            }
        }
    }

    /**
     * 从目录propertiesFilePath中载入指定配置文件的配置项到properties中
     * 
     * @param moduleNames 模块名，需与配置文件名一致（如EAM.properties，moduleName为EAM）
     */
    public static void loadProperties(String moduleNames)
    {
        // for example: /opt/netwatcher/pm4h2/app/conf/EAM.properties
        String fileName = dDEFAULTPROPPATH + File.separator + moduleNames + ".properties";
        File files = new File(fileName);
        if (!files.exists() || files.isDirectory())
        {
            return;
        }
        InputStream fis = null;
        // 将指定配置文件中的配置项均读入到properties对象中
        try
        {
            fis = new FileInputStream(files);
            properties.load(fis);
        }
        catch (FileNotFoundException exp)
        {
            new PropertiesUtils().doNothing();
        }
        catch (IOException exp)
        {
            new PropertiesUtils().doNothing();
        }
        finally
        {
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e)
                {
                    new PropertiesUtils().doNothing();
                }
            }
        }
    }

    /**
     * 根据key读取配置项的value
     * 
     * @param keys 配置项的key
     * 
     * @return 配置项的value
     */
    public static String getProperties(String keys)
    {
        String values = null;
        if (properties == null || properties.isEmpty())
        {
            loadProperties();
        }
        if (properties != null && !properties.isEmpty())
        {
            values = properties.getProperty(keys);
        }
        return values;
    }

    /**
     * 将所有的配置项存入一个map并返回此map
     * 
     * @return map 存放所有配置项的map
     */
    public static Map<String, String> getAllProperties()
    {
        Map<String, String> resultMaps = new HashMap<String, String>();
        if (!BeanUtils.isEmpty(properties))
        {
            Set<String> pNameSet = properties.stringPropertyNames();
            for (String pName : pNameSet)
            {
                resultMaps.put(pName, properties.getProperty(pName));
            }
        }
        return resultMaps;
    }

    /**
     * 读取配置文件存放路径到DEFAULTPROPPATH中
     */
    private void readPropPath()
    {
        String propFilePaths = "";
        // 1. 先从相对路径中读
        String curPath = System.getProperty("java.home");
        // /opt/netwatcher/pm4h2/app/conf
        if (!StringUtils.isEmpty(curPath)  && curPath.indexOf(BASEPATH) > -1)
        {
            String pathPrefix = curPath.substring(0, curPath.indexOf(BASEPATH)); // /opt/netwatcher/
            propFilePaths = pathPrefix + RELATIVEPATH;
        }
        // 2. 如果相对路径不存在，再从JVM参数中读
        if (!checkPathExist(propFilePaths))
        {
            propFilePaths = System.getProperty(dDEFAULTPROPPATHKEY);
        }
        if (!checkPathExist(propFilePaths))
        {
            propFilePaths = System.getProperty(dDEFAULTPROPPATHKEY.toLowerCase());
        }
        // 3. 如果上述两者都没有读到DEFAULTPROPPATH，再读默认值
        if (checkPathExist(propFilePaths))
        {
            dDEFAULTPROPPATH = propFilePaths;
        }
    }

    /**
     * 此方法实际上什么都没做。原因：某些模块加载此类时，其日志模块还未加载，故此类中的catch语句中不能调用日志的打印方法，为了通过代码静态检查，
     * 用此方法代替之。
     */
    private void doNothing()
    {
        dDEFAULTPROPPATHKEY = dDEFAULTPROPPATHKEY + "";
    }

    /**
     * 检查目录是否存在
     * 
     * @param paths 目录
     * 
     * @return true:存在；false:不存在
     */
    private boolean checkPathExist(String paths)
    {
        boolean existFlag1 = false;
        if (StringUtils.isEmpty(paths))
        {
            return existFlag1;
        }
        File file = new File(paths);
        if (file.exists())
        {
            existFlag1 = true;
        }
        return existFlag1;
    }

}
