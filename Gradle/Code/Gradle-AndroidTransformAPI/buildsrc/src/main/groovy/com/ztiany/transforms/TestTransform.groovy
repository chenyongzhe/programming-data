package com.ztiany.transforms

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.google.common.collect.Sets
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils

class TestTransform extends Transform {

    @Override
    String getName() {
        return "TestTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return Sets.immutableEnumSet(QualifiedContent.Scope.PROJECT)
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        println("transform is isIncremental = $isIncremental")
        if (!isIncremental) {
            try {
                outputProvider.deleteAll()
            } catch (e) {
                e.printStackTrace()
            }
        }
        // Transform的inputs有两种类型，一种是目录，一种是jar包，要分开遍历
        inputs.each {
            TransformInput input ->
                //对类型为“文件夹”的input进行遍历
                dirProcess(input, outputProvider)
                //对类型为jar文件的input进行遍历
                jarProcess(input, outputProvider)
        }
    }


    private
    static ArrayList<DirectoryInput> dirProcess(TransformInput input, TransformOutputProvider outputProvider) {
        println("-------------------------------------------------------------------TestTransform directoryInputs-------------------------------------------------------------------")
        input.directoryInputs.each {
            DirectoryInput directoryInput ->
                //E:\code\studio\my_github\Repository\Gradle\TransformAPI\app\build\intermediates\classes\release
                //E:\code\studio\my_github\Repository\Gradle\TransformAPI\app\build\intermediates\classes\debug
                println "origin --> ${directoryInput.file}"
                def dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                println("dest --> $dest")
                // 将input的目录复制到output指定目录
                FileUtils.copyDirectory(directoryInput.file, dest)
        }
        println()
    }

    private
    static ArrayList<JarInput> jarProcess(TransformInput input, TransformOutputProvider outputProvider) {
        println("-------------------------------------------------------------------TestTransform jarInputs-------------------------------------------------------------------")
        input.jarInputs.each {
            JarInput jarInput ->

                println("${jarInput.file.getAbsolutePath()}, status = ${jarInput.status}")

                def jarName = jarInput.name

                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())

                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }

                //生成输出路径
                def dest = outputProvider.getContentLocation(jarName + md5Name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                println("dest --> $dest")
                //将输入内容复制到输出
                FileUtils.copyFile(jarInput.file, dest)
        }

        println()
    }
}