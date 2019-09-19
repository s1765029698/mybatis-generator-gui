package com.zzg.mybatis.generator.plugins;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Project: mybatis-generator-gui
 *
 * @author slankka on 2018/3/11.
 */
public class CommonDAOInterfacePlugin extends PluginAdapter {

    private static final String DEFAULT_DAO_SUPER_CLASS = "BaseGenMapper";
    private static final FullyQualifiedJavaType PARAM_ANNOTATION_TYPE = new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param");
    private static final FullyQualifiedJavaType LIST_TYPE = FullyQualifiedJavaType.getNewListInstance();
    private static final FullyQualifiedJavaType SERIALIZEBLE_TYPE = new FullyQualifiedJavaType("java.io.Serializable");

    private List<Method> methods = new ArrayList<>();

    private ShellCallback shellCallback = null;

    public CommonDAOInterfacePlugin() {
        shellCallback = new DefaultShellCallback(false);
    }
    
    private boolean isUseExample() {
    	return "true".equals(getProperties().getProperty("useExample"));
	}

    public boolean override() {
        return "true".equals(getProperties().getProperty("override"));
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
//        boolean hasPk = introspectedTable.hasPrimaryKeyColumns();
//        JavaFormatter javaFormatter = context.getJavaFormatter();
//        String daoTargetDir = context.getJavaClientGeneratorConfiguration().getTargetProject();
//        String daoTargetPackage = context.getJavaClientGeneratorConfiguration().getTargetPackage();
        List<GeneratedJavaFile> mapperJavaFiles = new ArrayList<>();
//        String javaFileEncoding = context.getProperty("javaFileEncoding");
//        Interface mapperInterface = new Interface(daoTargetPackage + "." + DEFAULT_DAO_SUPER_CLASS);
//
//        if (stringHasValue(daoTargetPackage)) {
//            mapperInterface.addImportedType(PARAM_ANNOTATION_TYPE);
//            mapperInterface.addImportedType(LIST_TYPE);
//            mapperInterface.addImportedType(SERIALIZEBLE_TYPE);
//
//            mapperInterface.setVisibility(JavaVisibility.PUBLIC);
//            mapperInterface.addJavaDocLine("/**");
//            mapperInterface.addJavaDocLine(" * " + "DAO公共基类，由MybatisGenerator自动生成请勿修改");
//            mapperInterface.addJavaDocLine(" * " + "@param <Model> The Model Class 这里是泛型不是Model类");
//            mapperInterface.addJavaDocLine(" * " + "@param <PK> The Primary Key Class 如果是无主键，则可以用Model来跳过，如果是多主键则是Key类");
//			if (isUseExample()) {
//				mapperInterface.addJavaDocLine(" * " + "@param <E> The Example Class");
//			}
//            mapperInterface.addJavaDocLine(" */");
//
//            FullyQualifiedJavaType daoBaseInterfaceJavaType = mapperInterface.getType();
//            daoBaseInterfaceJavaType.addTypeArgument(new FullyQualifiedJavaType("Model"));
//            daoBaseInterfaceJavaType.addTypeArgument(new FullyQualifiedJavaType("PK extends Serializable"));
//			if (isUseExample()) {
//				daoBaseInterfaceJavaType.addTypeArgument(new FullyQualifiedJavaType("E"));
//			}
//
//            if (!this.methods.isEmpty()) {
//                for (Method method : methods) {
//                    mapperInterface.addMethod(method);
//                }
//            }
//
//            List<GeneratedJavaFile> generatedJavaFiles = introspectedTable.getGeneratedJavaFiles();
//            for (GeneratedJavaFile generatedJavaFile : generatedJavaFiles) {
//                CompilationUnit compilationUnit = generatedJavaFile.getCompilationUnit();
//                FullyQualifiedJavaType type = compilationUnit.getType();
//                String modelName = type.getShortName();
//                if (modelName.endsWith("DAO")) {
//                }
//            }
//            GeneratedJavaFile mapperJavafile = new GeneratedJavaFile(mapperInterface, daoTargetDir, javaFileEncoding, javaFormatter);
//            try {
//                File mapperDir = shellCallback.getDirectory(daoTargetDir, daoTargetPackage);
//                File mapperFile = new File(mapperDir, mapperJavafile.getFileName());
//                // 文件不存在
//                if (!mapperFile.exists()) {
//                    mapperJavaFiles.add(mapperJavafile);
//                }
//            } catch (ShellException e) {
//                e.printStackTrace();
//            }
//        }
        return mapperJavaFiles;
    }

    @Override
    public boolean clientGenerated(Interface interfaze,
                                   TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {
        if(!override()){
            return false;
        }
        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine(" * " + introspectedTable.getRemarks());
        interfaze.addJavaDocLine(" */");

        String daoSuperClass = "com.gjwl.core.base.dao." + DEFAULT_DAO_SUPER_CLASS;
        FullyQualifiedJavaType daoSuperType = new FullyQualifiedJavaType(daoSuperClass);
        FullyQualifiedJavaType daoSuperTypeAbbreviation = new FullyQualifiedJavaType(DEFAULT_DAO_SUPER_CLASS);

        String targetPackage = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetPackage();

        String domainObjectName = introspectedTable.getTableConfiguration().getDomainObjectName();
        FullyQualifiedJavaType baseModelJavaType = new FullyQualifiedJavaType(targetPackage + "." + domainObjectName);
        daoSuperType.addTypeArgument(baseModelJavaType);
        daoSuperTypeAbbreviation.addTypeArgument(baseModelJavaType);

        FullyQualifiedJavaType primaryKeyTypeJavaType = null;
        if (introspectedTable.getPrimaryKeyColumns().size() > 1) {
            primaryKeyTypeJavaType = new FullyQualifiedJavaType(targetPackage + "." + domainObjectName + "Key");
        }else if(introspectedTable.hasPrimaryKeyColumns()){
            primaryKeyTypeJavaType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType();
        }else {
            primaryKeyTypeJavaType = baseModelJavaType;
        }
        daoSuperType.addTypeArgument(primaryKeyTypeJavaType);
        daoSuperTypeAbbreviation.addTypeArgument(primaryKeyTypeJavaType);
		interfaze.addImportedType(primaryKeyTypeJavaType);

		if (isUseExample()) {
			String exampleType = introspectedTable.getExampleType();
			FullyQualifiedJavaType exampleTypeJavaType = new FullyQualifiedJavaType(exampleType);
			daoSuperType.addTypeArgument(exampleTypeJavaType);
            daoSuperTypeAbbreviation.addTypeArgument(exampleTypeJavaType);
			interfaze.addImportedType(exampleTypeJavaType);
		}

        interfaze.addAnnotation("@Mapper");
        FullyQualifiedJavaType mapperAnnoJavaType = new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper");
        interfaze.addImportedType(mapperAnnoJavaType);
        interfaze.addImportedType(baseModelJavaType);
        interfaze.addImportedType(daoSuperType);
        interfaze.addSuperInterface(daoSuperTypeAbbreviation);
        return true;
    }

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    private void interceptExampleParam(Method method) {
		if (isUseExample()) {
			method.getParameters().clear();
			method.addParameter(new Parameter(new FullyQualifiedJavaType("E"), "example"));
			methods.add(method);
		}
    }

    private void interceptPrimaryKeyParam(Method method) {
        method.getParameters().clear();
        method.addParameter(new Parameter(new FullyQualifiedJavaType("PK"), "id"));
        methods.add(method);
    }

    private void interceptModelParam(Method method) {
        method.getParameters().clear();
        method.addParameter(new Parameter(new FullyQualifiedJavaType("Model"), "record"));
        methods.add(method);
    }

    @Override
    public boolean sqlMapUpdateByExampleSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return super.sqlMapUpdateByExampleSelectiveElementGenerated(element, introspectedTable);
    }

    private void interceptModelAndExampleParam(Method method) {
		if (isUseExample()) {
			List<Parameter> parameters = method.getParameters();
			if (parameters.size() == 1) {
				interceptExampleParam(method);
			}else{
				method.getParameters().clear();
				Parameter parameter1 = new Parameter(new FullyQualifiedJavaType("Model"), "record");
				parameter1.addAnnotation("@Param(\"record\")");
				method.addParameter(parameter1);

				Parameter parameter2 = new Parameter(new FullyQualifiedJavaType("E"), "example");
				parameter2.addAnnotation("@Param(\"example\")");
				method.addParameter(parameter2);
				methods.add(method);
			}
		}
    }



    @Override
    public boolean clientCountByExampleMethodGenerated(Method method,
                                                       Interface interfaze, IntrospectedTable introspectedTable) {
//        interface
		if (isUseExample()) {
			interceptExampleParam(method);
		}
		return false;
	}


    @Override
    public boolean clientDeleteByExampleMethodGenerated(Method method,
                                                        Interface interfaze, IntrospectedTable introspectedTable) {
        if (isUseExample()) {
			interceptExampleParam(method);
		}
        return false;
    }


    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method,
                                                           Interface interfaze, IntrospectedTable introspectedTable) {
    	interceptPrimaryKeyParam(method);
        return false;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze,
                                                  IntrospectedTable introspectedTable) {
        interceptModelParam(method);
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method,
                                                                 Interface interfaze, IntrospectedTable introspectedTable) {
        if (isUseExample()) {
			interceptExampleParam(method);
			method.setReturnType(new FullyQualifiedJavaType("List<Model>"));
		}
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method,
                                                                    Interface interfaze, IntrospectedTable introspectedTable) {
        if (isUseExample()) {
			interceptExampleParam(method);
			method.setReturnType(new FullyQualifiedJavaType("List<Model>"));
		}
        return false;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method,
                                                           Interface interfaze, IntrospectedTable introspectedTable) {
    	interceptPrimaryKeyParam(method);
        method.setReturnType(new FullyQualifiedJavaType("Model"));
        return false;
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method,
                                                                 Interface interfaze, IntrospectedTable introspectedTable) {
        if (isUseExample()) {
			interceptModelAndExampleParam(method);
		}
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method,
                                                                 Interface interfaze, IntrospectedTable introspectedTable) {
        if (isUseExample()) {
			interceptModelAndExampleParam(method);
		}
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method,
                                                                    Interface interfaze, IntrospectedTable introspectedTable) {
        if (isUseExample()) {
			interceptModelAndExampleParam(method);
		}
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method,
                                                                    Interface interfaze, IntrospectedTable introspectedTable) {
        interceptModelParam(method);
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (isUseExample()) {
			interceptModelAndExampleParam(method);
		}
        return false;
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (isUseExample()) {
			interceptModelAndExampleParam(method);
		}
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method,
                                                                    Interface interfaze, IntrospectedTable introspectedTable) {
    	interceptModelParam(method);
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(
            Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        interceptModelParam(method);
        return false;
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        interceptModelParam(method);
        return false;
    }

    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
        List<GeneratedXmlFile> result = new ArrayList<GeneratedXmlFile>();
        String targetProjectDir = context.getSqlMapGeneratorConfiguration().getTargetProject();
        String mapperFileName = getMyBatis3XmlMapperExtFileName(introspectedTable);
        String targetPackage = introspectedTable.getMyBatis3XmlMapperPackage();

        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(targetPackage, "."); //$NON-NLS-1$
        while (st.hasMoreTokens()) {
            sb.append(st.nextToken());
            sb.append(File.separatorChar);
        }

        File targetDirectory = new File(targetProjectDir, sb.toString());
        File targetFile = new File(targetDirectory, mapperFileName);


        if(!targetFile.exists()){
            Document document = new Document(XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID, XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
            XmlElement mapperEle = new XmlElement("mapper");
            mapperEle.addAttribute(new Attribute("namespace", introspectedTable.getMyBatis3SqlMapNamespace()));

            TextElement textElement = new TextElement("<!-- Extend " + introspectedTable.getTableConfiguration().getMapperName() + ".xml -->");
            mapperEle.addElement(textElement);
            document.setRootElement(mapperEle);

            GeneratedXmlFile gxf = new GeneratedXmlFile(document,
                    mapperFileName, targetPackage,
                    targetProjectDir,
                    true, context.getXmlFormatter());
            result.add(gxf);
        }
        return result;
    }

    private String getMyBatis3XmlMapperExtFileName(IntrospectedTable introspectedTable) {
        return introspectedTable.getTableConfiguration().getMapperName() + "Ext.xml";
    }


}
