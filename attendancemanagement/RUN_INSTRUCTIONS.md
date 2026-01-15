# How to Run the Attendance Management System

## Step-by-Step Guide

### Prerequisites Check

Before running, ensure you have:
- ✅ **Java 17 or higher** installed
  - Check: `java -version`
- ✅ **Maven 3.6+** installed
  - Check: `mvn -version`
- ✅ **MySQL** installed and running
  - Check: MySQL service is running on your system

---

## Step 1: Database Setup

### Option A: Using MySQL Command Line
1. Open MySQL command line or MySQL Workbench
2. Run this command:
```sql
CREATE DATABASE attendance_management;
```

### Option B: Automatic Creation (Recommended)
The application will automatically create the database if it doesn't exist (configured in `application.properties`).

---

## Step 2: Configure Database Credentials

1. Open `src/main/resources/application.properties`
2. Update these lines with your MySQL credentials:
```properties
spring.datasource.username=root          # Your MySQL username
spring.datasource.password=yourpassword # Your MySQL password (leave empty if no password)
```

**Note:** If your MySQL has no password, leave it empty: `spring.datasource.password=`

---

## Step 3: (Optional) Configure Email Settings

If you want email notifications to work:

1. Open `src/main/resources/application.properties`
2. Update email settings:
```properties
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

**For Gmail:**
- Enable 2-Factor Authentication
- Generate an App Password (Settings → Security → App Passwords)
- Use the App Password, NOT your regular password

**To disable email notifications:**
```properties
app.notifications.email.enabled=false
```

---

## Step 4: Run the Application

### Method 1: Using Maven Command Line (Recommended)

1. **Open Terminal/Command Prompt**
2. **Navigate to project directory:**
   ```bash
   cd C:\Users\Asus\Downloads\attendancemanagement\attendancemanagement
   ```

3. **Build the project:**
   ```bash
   mvn clean install
   ```

4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

### Method 2: Using Maven Wrapper (Windows)

1. **Navigate to project directory**
2. **Run:**
   ```bash
   .\mvnw.cmd spring-boot:run
   ```

### Method 3: Using IDE (IntelliJ IDEA / Eclipse / VS Code)

#### IntelliJ IDEA:
1. Open the project folder
2. Wait for Maven to download dependencies
3. Right-click on `AttendancemanagementApplication.java`
4. Select **Run 'AttendancemanagementApplication'**

#### Eclipse:
1. Import as Maven Project
2. Right-click project → Run As → Spring Boot App

#### VS Code:
1. Install "Extension Pack for Java"
2. Open the project
3. Press `F5` or click Run

---

## Step 5: Access the Application

Once the application starts, you'll see:
```
Started AttendancemanagementApplication in X.XXX seconds
```

1. **Open your web browser**
2. **Navigate to:** `http://localhost:8080`
3. You'll be redirected to the login page

---

## Step 6: Login with Default Credentials

The system automatically creates default users on first run:

### Teacher Account:
- **Username:** `teacher1`
- **Password:** `password`
- **Access:** Teacher Portal

### Student Account:
- **Username:** `student1`
- **Password:** `password`
- **Access:** Student Portal

---

## Troubleshooting

### Issue: Port 8080 already in use
**Solution:** Change the port in `application.properties`:
```properties
server.port=8081
```
Then access: `http://localhost:8081`

### Issue: Database connection error
**Solutions:**
1. Check MySQL is running
2. Verify database credentials in `application.properties`
3. Ensure database exists or allow auto-creation
4. Check MySQL port (default: 3306)

### Issue: Maven build fails
**Solutions:**
1. Check internet connection (Maven downloads dependencies)
2. Verify Java version: `java -version` (must be 17+)
3. Try: `mvn clean install -U` (force update)

### Issue: Application won't start
**Solutions:**
1. Check console for error messages
2. Verify all dependencies are downloaded
3. Check `application.properties` syntax
4. Ensure MySQL is accessible

### Issue: Can't login
**Solutions:**
1. Use default credentials: `teacher1`/`password` or `student1`/`password`
2. Check database has users (first run creates them automatically)
3. Clear browser cache and try again

---

## Quick Start Commands Summary

```bash
# Navigate to project
cd C:\Users\Asus\Downloads\attendancemanagement\attendancemanagement

# Build project
mvn clean install

# Run application
mvn spring-boot:run

# Or use wrapper (Windows)
.\mvnw.cmd spring-boot:run
```

---

## What Happens on First Run?

1. ✅ Database tables are automatically created
2. ✅ Default teacher user is created (`teacher1` / `password`)
3. ✅ Default student user is created (`student1` / `password`)
4. ✅ Student is automatically assigned to teacher as mentor

---

## Next Steps After Running

1. **Login as Teacher:**
   - Mark attendance for students
   - View student attendance history
   - Respond to student queries

2. **Login as Student:**
   - View your attendance records
   - Check attendance percentage
   - Raise queries to your mentor

3. **Register New Users:**
   - Click "Register" on login page
   - Create new teachers or students
   - New students are automatically assigned to first available teacher

---

## Stopping the Application

- **In Terminal:** Press `Ctrl + C`
- **In IDE:** Click the Stop button in the console

---

## Need Help?

- Check the `README.md` for detailed documentation
- Review error messages in the console
- Verify all prerequisites are installed correctly

