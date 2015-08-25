package com.lily.dap;


/**
 * <code>Constants</code>
 * <p>ϵͳ��������</p>
 *
 * @author ��ѧģ
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
     * <code>BASE_PACKAGE</code> ϵͳ�İ�����·��
     */
    public static final String BASE_PACKAGE = "com.lily.dap";
    
    /**
     * <code>PACKAGE_ENTITY</code> ʵ����������·��
     */
    public static final String PACKAGE_ENTITY = BASE_PACKAGE + ".entity";
    
    /**
     * <code>PACKAGE_SERVICE</code> ����������·��
     */
    public static final String PACKAGE_SERVICE = BASE_PACKAGE + ".service";
    
    /**
     * <code>PACKAGE_ACTION</code> WEB��Action������·��
     */
    public static final String PACKAGE_ACTION = BASE_PACKAGE + ".web.action";
}
