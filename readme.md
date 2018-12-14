# ijkplayer 

## 编辑(Mac环境)

### Step1 安装git和yasm
 
### Step2 配置android sdk 和 ndk环境

ndk不建议用sdkmanager中的ndk-bundle,问题很多，建议去[官网](https://developer.android.google.cn/ndk/downloads/)下载，支持最低版本10e,高版本的支持也不太友好，在这里使用版本10e。

```bash
export ANDROID_HOME=/Users/shenyonghe/sdk
export NDK_ROOT=/Users/shenyonghe/android-ndk-r10e
export PATH=${PATH}:${ANDROID_HOME}/tools
export PATH=${PATH}:${ANDROID_HOME}/platform-tools 
export PATH=${PATH}:${NDK_ROOT}
```
 
### Step3 拉代码

```bash
git clone https://github.com/Bilibili/ijkplayer.git  ijkplayer-source
cd ijkplayer-source
git checkout -B k0.8.8
```

### Step4 初始化android

```bash
./init-android.sh
```

### Step5 编译脚本配置

就是自动化编译时的一些配置选项，比如支持什么协议啊，支持什么音视频类型等， 
这个配置文件是：config/module.sh，你喜欢可以打开看看这个文件： 
比如这里是配置处理什么类型的数据的，enable启用，disable禁用。

另外官方给我们提供了三个模板给我们使用：

* module-default.sh：默认，如果你喜欢更多类型可以用这个； 
* module-lite-hevc.sh：如果您更喜欢较小的二进制大小的编解码器/格式（包括hevc功能） 
* module-lite.sh：如果您更喜欢较小的二进制大小的编解码器/格式（默认情况下）

最新版已经将module.sh指向module-lite.sh也可以自己修改：

```bash
rm module.sh
//建立链接
ln -s module-lite.sh module.sh
source module.sh
```

### Step6 初始化支持https

```bash
cd ijkplayer-source/
./init-android-openssl.sh
```

### Step7 clean

```bash
cd android/contrib
./compile-openssl.sh clean
./compile-ffmpeg.sh clean
```

### Step8 编译openssl

```bash
./compile-openssl.sh all
```

### Step9 编译ffmpeg

如果想编译所有版本的so库，就跟all，如果是特定CPU架构就跟cpu架构，比如：./compile-ffmpeg.sh armv7a 

```bash
./compile-ffmpeg.sh all
```

### Step10 编译ijkplayer

加all默认编译所有架构的so库，不加默认只编译armv7a架构！

```bash
./compile-ijk.sh all
```

编译需要漫长的等待，编译成功后，会在android目录下生成一个ijkplayer的工程：

## 使用

### 1 直接打开android/ijkplayer项目

可以将自己需要的ijkplayer-xxx作为lib导入项目

### 2 删减无关依赖，生成aar

1 build解除ijkplayer-example除ijkplayer-java。

```xml
//    all32Compile project(':ijkplayer-armv5')
//    all32Compile project(':ijkplayer-armv7a')
//    all32Compile project(':ijkplayer-x86')
//    all64Compile project(':ijkplayer-armv5')
//    all64Compile project(':ijkplayer-armv7a')
//    all64Compile project(':ijkplayer-arm64')
//    all64Compile project(':ijkplayer-x86')
//    all64Compile project(':ijkplayer-x86_64')
```

2 接着打开ijkplayer-java，新建一个libs文件夹, 同时打开ijkplayer-armv7a/main/libs，把里面的armeabi-v7a文件夹整个拷到ijkplayer-java的libs文件夹下。记得指定jni目录，不然找不到so库。

```xml
sourceSets {
    main {
        jniLibs.srcDirs = ['libs']
    }
}
```

3 ijkplayer-java生成aar：gradle->ijkplayer-java->assembleRelease

4 主项目引入aar

```xml
repositories {
    flatDir {
        dirs 'libs'
    }
}

sourceSets {
    main {
        jniLibs.srcDirs = ['libs']
    }
}

compile (name: 'ijkplayer-java-release', ext: 'aar')
```

