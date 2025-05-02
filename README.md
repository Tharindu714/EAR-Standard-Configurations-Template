# ♨️ Delta Codex EAR Project

> **Goal:** Demonstrate a complete Jakarta EE 9 EAR packaging with Core, EJB, Web, and EAR modules, ready for real-world deployment.

---

## 📁 Multi-Module Structure

At the root of the repository:

```
Delta-Codex-EAR/
├── pom.xml           # Parent POM (packaging: pom)
├── core/             # Java library module (models, DTOs, DAOs)
├── ejb/              # EJB module (business logic beans)
├── web/              # Web module (WAR: servlets, JSPs)
└── ear/              # EAR module (aggregator)
```

---

## 1. Parent POM Configuration (`pom.xml`)

* **Packaging:** `pom` to orchestrate submodules.
* **Modules Defined:**

  ```xml
  <modules>
    <module>core</module>
    <module>ejb</module>
    <module>web</module>
    <module>ear</module>
  </modules>
  ```
* **Dependency Management:** Centralizes Jakarta EE API versions for consistency.
* **Build Settings:** Sets `<finalName>${project.name}</finalName>` for unified artifact naming.

---

## 2. Core Module (`core/pom.xml`)

* **Packaging:** `jar`
* **Content:** Domain models (`Product.java`), DTOs, and DAOs under `com.deltacodex.ear.core`.
* **Role:** Provides shared types; ends up in `lib/` inside the EAR.

---

## 3. EJB Module (`ejb/pom.xml`)

* **Packaging:** `ejb`
* **Dependencies:**

  ```xml
  <dependency>
    <groupId>com.deltacodex.ear</groupId>
    <artifactId>core</artifactId>
    <version>1.0</version>
  </dependency>
  ```
* **Components:** Stateless and stateful beans (e.g., `ProductServiceBean`), remote interfaces (`@Remote`).
* **Jakarta EE APIs:** Provided scope for EJB and logging APIs.

---

## 4. Web Module (`web/pom.xml`)

* **Packaging:** `war`
* **Dependencies:** EJB client stub for remote lookup:

  ```xml
  <dependency>
    <groupId>com.deltacodex.ear</groupId>
    <artifactId>ejb</artifactId>
    <version>1.0</version>
    <type>ejb-client</type>
  </dependency>
  ```
* **Structure:**

  ```
  ```

src/main/webapp/
├─ WEB-INF/
│   └─ web.xml
└─ index.jsp

````
- **Servlets:** Lookup and invoke EJB business methods.

---

## 5. EAR Module (`ear/pom.xml`)

- **Packaging:** `ear`  
- **Parent:** Inherits from root POM (`com.deltacodex.ear:Delta-Codex-EAR:1.0`).
- **Maven EAR Plugin Configuration:**
```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-ear-plugin</artifactId>
  <version>3.3.0</version>
  <configuration>
    <defaultLibBundleDir>lib</defaultLibBundleDir>
    <modules>
      <ejbModule>
        <groupId>com.deltacodex.ear</groupId>
        <artifactId>ejb</artifactId>
        <bundleFileName>EJB-Module.jar</bundleFileName>
      </ejbModule>
      <webModule>
        <groupId>com.deltacodex.ear</groupId>
        <artifactId>web</artifactId>
        <bundleFileName>WEB-Module.war</bundleFileName>
        <contextRoot>/ee-app</contextRoot>
      </webModule>
    </modules>
  </configuration>
</plugin>
````

* **`application.xml`** (`src/main/application/META-INF/application.xml`):

  ```xml
  <application xmlns="https://jakarta.ee/xml/ns/jakartaee" version="9">
    <module>
      <ejb>EJB-Module.jar</ejb>
    </module>
    <module>
      <web>
        <web-uri>WEB-Module.war</web-uri>
        <context-root>/ee-app</context-root>
      </web>
    </module>
  </application>
  ```

---

## 📦 Build & Deployment Steps

1. **Install All Modules** (from project root):

   ```bash
   mvn clean install
   ```

   * Installs `core`, `ejb`, and `web` artifacts into your local repo.

2. **Package the EAR:**

   ```bash
   cd ear
   mvn clean package
   ```

   * Results in `target/Delta-Codex-EAR.ear`.

3. **Deploy to Your Application Server:**

   * **GlassFish/Payara:**

     ```bash
     cp target/Delta-Codex-EAR.ear $GLASSFISH_HOME/glassfish/domains/domain1/autodeploy/
     ```
   * **WildFly/JBoss:**

     ```bash
     cp target/Delta-Codex-EAR.ear $WILDFLY_HOME/standalone/deployments/
     ```

4. **Verify Deployment:**

   * Browse to `http://localhost:8080/ee-app` to load the web module.
   * Invoke your EJBs via servlet endpoints or remote client.

---

## 🛠️ Configuration Insights

* **`defaultLibBundleDir`** places shared JARs (e.g., `core.jar`) under `lib/` in the EAR.
* **Module Dependencies** are resolved in the order defined in `application.xml`.
* **Classloading Policy:** EAR-level libraries load first, then module-specific classes.
* **Isolated Context Roots:** Each web module gets its own context (here `/ee-app`).

---

## 🔮 Next Steps & Enhancements

* **Persistence Module:** Add a JPA-backed EJB with `persistence.xml`.
* **Security:** Enable Jakarta Security for authentication/authorization.
* **Messaging:** Introduce JMS and MDBs within the EJB module.
* **Client Module:** Create an App-Client JAR to remotely invoke EJBs via JNDI.

---

> This README provides a step-by-step guide—from Maven multi-module setup to packaging and deploying a unified EAR. Customize further to suit your enterprise needs!
