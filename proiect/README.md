Un proiect facut folosind Java EE care are urmatoarele functionalitati:
- doua servicii, `user-service` si `submission-service` care sunt majoritar acelasi serviciu, doar cu modele schimbate si REST controllere diferite
- o parte de Multi-Tenant la nivel de baza de date
- o modalitate de a schimba tenant-ul bazat pe un header HTTP `X-Tenant-ID`
- autentificare folosind JWT

# MultiTenancy

In java/ro/tedyst/userservice/config avem:
- `MapMultiTenantConnectionProvider.java` care se ocupa de creearea conexiunilor la bazele de date si initializarea schemelor acestora
    - Initializarea schemelor nu s-a putut face automat din cauza implementarii Hibernate de Multi-Tenancy care nu permite acest lucru, asa ca am folosit:
    ```java
    StandardServiceRegistry serviceRegistry =
                        new StandardServiceRegistryBuilder().applySettings(configProperties).build();
    ```
        pentru a realiza acest lucru
    - Pentru configurarea conexiunilor am folosit `DriverManagerConnectionProviderImpl` si am incarcat parametrii folosind `ClassLoader`.
    - Pentru incarcarea configuratiilor, am folosit un fisier `tenants-info.properties` care contine informatii despre conexiunile la bazele de date si denumirea fiecarui tenant. In interiorul acestui fisier se va afla un map cu cheia fiind denumirea tenantului si valoarea fiind un map cu cheia fiind numele proprietatii si valoarea fiind un fisier de configurare.
    - In folderul `tenants/` se afla fisierele de configurare pentru fiecare tenant in parte. Aceste fisiere contin informatii despre conexiunea la baza de date, cum ar fi `hibernate.connection.url`, `hibernate.connection.username`, `hibernate.connection.password`, etc.

- `TenantIdentifierResolver.java` care se ocupa de identificarea tenantului curent
    - Pentru a identifica tenantul curent, am folosit un `ThreadLocal` care retine numele tenantului curent.

In java/ro/tedyst/userservice/filters avem:
- `TenantFilter.java` care preia header-ul `X-Tenant-ID` si seteaza tenantul curent in `ThreadLocal` folosind `TenantIdentifierResolver`.

Se pot folosi baze de date diferite intre cele doua servicii, insa este de preferat sa se foloseasca aceeasi baza de date pentru a evita problemele de sincronizare.

# Autentificare

In java/ro/tedyst/userservice/secure avem o clasa denumita `MPJWTToken.java` care se ocupa de generarea si validarea token-urilor JWT. Am folosit Java EE Security API pentru a realiza autentificarea si autorizarea.

# Serviciile

In rest, sunt servicii clasice care se ocupa de operatii CRUD pe baza de date. Principalele sunt `UserService` si `UserGroupService` care se ocupa de autorizarea utilizatorilor si a grupurilor de utilizatori. Acestea sunt protejate de autentificare si autorizare.

Mai exista si `SubmissionService` care se ocupa de operatii CRUD pe baza de date pentru submisii. 