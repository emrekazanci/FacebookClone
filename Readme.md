# MİKROSERVİS İŞLEMLERİ VE NOTLARIM
## 1. Kurulum adımları
    1.1 Boş gradle projesi açtık.
    1.2 dependecies.gradle dosyasını kodladık.
        1.2.1 ext {} bloğu içerisindeki kütüphaneleri projemize dahil ettik.
        1.2.2 versions{} bloğu içerisindeki kütüphanelerin verisyonlarını belirledik.
        1.2.3 libs{} bloğu içerisinde kullanacağımız kütüphaneleri belirledik.
    1.3 build.gradle dosyasını kodladık.
    1.4 uygulamamızın mimarisi gereği ihtiyaç duyduğumuz mikroservisleri belirleyerek 
    onları modül olarak ekledik.
    1.5 her bir modül için eklememiz gereken aşağıdaki kod bloğunu 
    build.gradle dosyalarına ekledik.
```
buildscript {
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${versions.springBoot}")
    }
}
```
    1.6. tüm modüllerimize monolitik mimaride kullandığımız default kodlamaları ekledik.
    1.7. Eğer bir modül içinde kullanmak istediğimiz özel bağımlılıklar var ise bunları 
    build.gradle dosyalarına ekledik.

## 2. MongoDB kurulum ve kullanım

### 2.1. MongoDB Docker üzerinde çalıştırmak
    Docker kurulu olan bir sistem üzerinde command satırına girerek aşağıda yeralan 
    komutları yazarak docker üzerinde çalıştırıyoruz.
    
    > docker run --name dockermongo -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=BilgeAdmin -e MONGO_INITDB_ROOT_PASSWORD=root -d mongo
    Bir diğer isim şifre admin, root

### 2.2 MongoDB ye bağlanmak
    DİKKAT !!!
    Mongodb kullanırken admin kullanıcısı ve şifrelerini veritabanlarına erişmek için kullanmayız.
    Yeni bir veritabanı oluşturmak için admin kullanıcısı ile işlem yapılır. BU veri tabanına atanmak üzere 
    yeni bir kullanıcı oluşturulur. Böylelikle admin kullanıcısına ihtiyaç kalmadan DB üzerinde işlem gerçeştirilir.
    1- Öncelikle bir veritabanı oluşturuyorsunuz. (FacebookDB)
    2- mongosh kullnarak konsolda 'use DB_ADI' yazıyorsunuz ve ilgili DB ye geçiyorsunuz.
    3- yine aynı ekran yeni bir kullanıcı oluşturmanız gereklidir. Bu kullanıcı yetkili olacaktır.
    db.createUser({user: "Java7User", pwd: "root", roles: ["readWrite","dbAdmin"]})
    
## 3. RabbitMQ kurulum ve kullanım
### 3.1. RabbitMQ Docker üzerinden çalıştırmak
    docker run -d --name my-rabbitmq -e RABBITMQ_DEFAULT_USER=java7 -e RABBITMQ_DEFAULT_PASS=root -p 5672:5672 -p 15672:15672 rabbitmq:3-management
### 3.2. RabbitMQ ya bağlanmak
    gradle import -> org.springframework.boot:spring-boot-starter-amqp:
    Rabbit Config yapılandırılır ve kuyruk yapısı kurulur.

## 4. Zipkin Server kurmak ve kullanmak
    docker run --name zipkinfb -d -p 9411:9411 openzipkin/zipkin
    Zipkin için gerekli bağımlılıkalar:
    'org.springframework.cloud:spring-cloud-starter-sleuth:3.1.7'
    'org.springframework.cloud:spring-cloud-sleuth-zipkin:3.1.7'

    application.yml içine configration eklenmeli
          zipkin:
            enabled: true
            base-url: http://localhost:9411
            service:
              name: config-server
## 5. Redis kurulum ve kullanım
    docker run --name localredis -d -p 6379:6379 redis
    Redis için gereken bağımlılıklar:
        "org.springframework.boot:spring-boot-starter-data-redis:${versions.springBoot}"
    Redis için bağlantı kodlamalarını yapmamız gerrkiyor.
        @Bean
        public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(
                new RedisStandaloneConfiguration("localhost", 6379));
        }
## 6. ElasticSearch kurulum ve kullanım
    Dikkat!!
    Spring ile kullanımında sürüm önemlidir. Hangi Spring boot sürümünü kullandıysanız ona 
    uygun bir ElasticSearch sürümü kullanmalısınız.
    - docker network create somenetwork
    - docker run -d --name elasticsearch --net somenetwork -p 9200:9200 -p 9300:9300 -e ES_JAVA_OPTS="-Xms512m -Xmx1024m" -e "discovery.type=single-node" elasticsearch:7.17.9
    
## 7. Projenin Docker image olarak oluşturulması
    Herhangi bir projenin paket haline getirilmesi için öncelikle
    1- Gradle -> [Projenin Adı] -> Tasks -> build -> build
    2- build işlemi tamamlandıktan sonra -> buildDependents
    Bu iki adımdan sonra projenin altında build klasörü oluşur bu klasör içerisinde jar dosyası oluşur.
### 7.1 Dockerfile
    DOcker file bir docker image oluşturmak için kullanılan özel bir dosyadır.
    Bunun içerisinde ihtiyaç duyulan tüm komutlar yer alır.
    BU komutlar ile microsevisimizin çalışması için gerekli parametreler ve bağımlılıklar belirtilir.

    # İşletim sistemi ve Java JDK eklenir.
    # FROM amazoncoretto:17 -- amazon corretto ile java jdk17 sürüöü kullanılacak demektir.
    FROM azul/zulu-openjdk-alpine:17.0.7
    # build aldığımız jar dosyasını docker imajımızın içine kopyalıyoruz.
    COPY ConfigServerGit/build/libs/ConfigServerGit-v.1.0.jar app.jar
    # docker imajımızın çalışması için java uygulamamızı tetikliyoruz.
    ENTRYPOINT ["java","-jar","/app.jar"]

## Docker Image oluşturmak
    DİKKAT
    Docker image oluştururken docker.hub üzerindeki repoya gönderilmek istenilen image lar için 
    isimlendirmeyi doğru yapmak gereklidir.
    hub.docker repo adınız/ image adınız:versiyon numarası
    DİKKAT!! ifadenin sonunda olan nokta (.) unutulmamalıdır.
    docker build -t emrekazanci/java7-configservergit:v.1.0 .
    docker build --platform linux/amd64 -t emrekazanci/java7-configservergit:v.1.1 .    -> Mac için!
    DİKKAT!!
    Her bir değişiklik yapıldığında bu adımların tekrarlanması gerekli ki bu cloud a aktarılabilsin!

    docker build --platform linux/amd64 -t emrekazanci/java7-authservice:v.1.0 .    -> AuthService için
    docker build --platform linux/amd64 -t emrekazanci/java7-userservice:v.1.0 .    -> UserService için
    docker build --platform linux/amd64 -t emrekazanci/java7-gatewayservice:v.1.0 .    -> ApiGateway için