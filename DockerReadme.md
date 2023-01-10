# DOCKER KULLANIMI

## IMAGE OLUSTURMA
    docker build --build-arg JAR_FILE=<build path >-t <image name> .
    docker build --build-arg JAR_FILE=auth-service/build/libs/auth-service-v.0.0.1.jar -t java4authservice:v003 .
    build path: servisimizden build aldýðýmýz zaman oluþan jar dosyasýnýn konumu
    image name: oluþturacaðýmýz image e vereceðimiz isim ( versiyon numarasý vermeyi unutmayýn !!! )
## IMAGE UZERÝNDEN CONTAINER CALISTIRMA
    docker run -d -p dýsport:içport java4authservice:v003
    docker run -d -p 9091:8090 java4authservice:v003
    içport: application yml da uygulamanýn ayaða kalktýðý port
    dýþport: containerýn dýþarýya açýldýðý port istekler bu porta gelecek bu port iç porta yonlendirecek 

## NETWORK OLUSTURMA
    docker network ls: var olan networklerimizi listeleme
    docker network rm somenetwork : network adýyla networkumuzu silme
    docker network create --driver bridge --subnet <ag portlarý > --gateway 182.18.0.1 < network name>
    docker network create --driver bridge --subnet 182.18.0.1/24 --gateway 182.18.0.1 java4-network
    ag portlarý: networkumuzdeki ip aralýðýný belirlediðmiz yer
    network create komutu ile bir network olusturabiliriz
### NETWORKE CONTAINER BAGLAMA
    docker run --name java4-postgresql -e POSTGRES_PASSWORD=root --net java4-network -d -p 5656:5432 postgres
    java4-postgresql adýnda bir postgresl container'ý olusturduk 
    --net java4-network komutu ile olusturdugumuz java4- networkune  postgresqlimizi baðladýk
    daha sonra apllication yml da db_url imizi deðiþtirdik
    url: jdbc:postgresql://localhost:5432/Java4SocialMediaAuthDb yerine artýk 
    jdbc:postgresql://java4-postgresql/Java4SocialMediaAuthDb  yazdýk burda 
    localhost yerine aslýnda olusturdugumuz postgresql containernýn ismini verdik 
    ve pg adminden register ile 5656 daki postgeslimize baðlandýk ve Java4SocialMediaAuthDb adýnda bir databse olusturduk
    daha sonra uygulamamýzý tekrar build edip uygulamamýzdan bir image olusturduk 
    docker build --build-arg JAR_FILE=auth-service/build/libs/auth-service-v.0.0.1.jar -t java4authservice:v003 .
    ve bu image'i çalýstýrýken olusturduðumuz java4-networkune asagýdaki kodla baðladýk
    docker run --net java4-network -d -p 9091:8090 java4authservice:v003
    
    




