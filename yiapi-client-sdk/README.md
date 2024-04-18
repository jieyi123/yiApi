# 自定义starter
使用 starter 的好处就是，开发者引入后可以直接在 application.yml 中进行配置，自动创建相应的客户端。这样使得开发过程更加简单便捷，无需过多关注底层实现细节，而是专注于配置和使用。
1. 删除pom.xml文件中的build部分

2. 设置配置文件 resource/META-INF/spring.factories

3. 打包 Lifecycle->install

4. 在pom.xml中引入当前依赖