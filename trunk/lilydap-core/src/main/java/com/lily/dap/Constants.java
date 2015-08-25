package com.lily.dap;


/**
 * <code>Constants</code>
 * <p>系统常量定义</p>
 *
 * @author 邹学模
 */
public class Constants {
    //~ Static fields/initializers =============================================
   
    /** The name of the ResourceBundle used in this application */
    public static final String BUNDLE_KEY = "ApplicationResources";

    /** File separator from System properties */
    public static final String FILE_SEP = System.getProperty("file.separator");

    /** User home from System properties */
    public static final String USER_HOME = System.getProperty("user.home") + FILE_SEP;
    
    /**
     * <code>BASE_PACKAGE</code> 系统的包基本路径
     */
    public static final String BASE_PACKAGE = "com.lily.dap";
    
    /**
     * <code>PACKAGE_ENTITY</code> 实体对象包基本路径
     */
    public static final String PACKAGE_ENTITY = BASE_PACKAGE + ".entity";
    
    /**
     * <code>PACKAGE_SERVICE</code> 服务层包基本路径
     */
    public static final String PACKAGE_SERVICE = BASE_PACKAGE + ".service";
    
    /**
     * <code>PACKAGE_ACTION</code> WEB层Action包基本路径
     */
    public static final String PACKAGE_ACTION = BASE_PACKAGE + ".web.action";
}
