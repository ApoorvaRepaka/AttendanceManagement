# Attendance Management System

A comprehensive web-based application for managing student attendance with separate portals for teachers and students, featuring mentor-student communication and automated notifications.

## Features

### Teacher Portal
- Secure login for teachers
- View list of assigned students
- Mark daily attendance (Present/Absent)
- Track attendance history of students
- View and respond to student queries (mentor role)

### Student Portal
- Secure login for students
- View personal attendance records
- Track attendance percentage
- Raise queries related to attendance or academics
- Receive responses from assigned mentor

### Mentor System
- Each student is assigned to one mentor (teacher)
- Mentors can view queries raised by their students
- Mentors can respond directly through the system

### Notifications
- Email notifications for low attendance (below 75%)
- Email notifications when mentor responds to queries
- SMS notification support (configurable)

## Tech Stack

- **Backend:** Spring Boot 4.0.1 (Java 17)
- **Frontend:** Thymeleaf, HTML, CSS
- **Database:** MySQL
- **Security:** Spring Security (Role-based authentication)
- **Architecture:** MVC (Model–View–Controller)

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 5.7+ or MySQL 8.0+
- (Optional) Email account for notifications (Gmail recommended)

## Setup Instructions

### 1. Database Setup

1. Create a MySQL database:
```sql
CREATE DATABASE attendance_management;
```

2. Update `src/main/resources/application.properties` with your database credentials:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 2. Email Configuration (Optional)

To enable email notifications, update the email settings in `application.properties`:

```properties
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

**Note:** For Gmail, you'll need to:
1. Enable 2-Factor Authentication
2. Generate an App Password (not your regular password)
3. Use the App Password in the configuration

### 3. Build and Run

1. Navigate to the project directory:
```bash
cd attendancemanagement
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

4. Access the application at: `http://localhost:8080`

## Default Credentials

The system automatically creates default users on first run:

**Teacher:**
- Username: `teacher1`
- Password: `password`
- Teacher ID: `T001`

**Student:**
- Username: `student1`
- Password: `password`
- Student ID: `S001`

**Note:** The default student is automatically assigned to the default teacher as their mentor.

## Usage Guide

### For Teachers

1. **Login** with your teacher credentials
2. **Mark Attendance:**
   - Navigate to "Mark Attendance"
   - Select a student, date, and status (Present/Absent)
   - Add optional remarks
   - Submit

3. **View Student Attendance:**
   - Go to "My Students"
   - Click "View Attendance" for any student
   - See attendance history and percentage

4. **Respond to Queries:**
   - Navigate to "View Queries"
   - Click "Respond" on pending queries
   - Enter your response and submit

### For Students

1. **Login** with your student credentials
2. **View Attendance:**
   - Dashboard shows attendance percentage
   - Click "View Attendance" for detailed records
   - System shows warning if attendance is below 75%

3. **Raise Query:**
   - Click "Raise Query" or "My Queries"
   - Enter your question
   - Submit to send to your mentor

4. **View Query Responses:**
   - Go to "My Queries"
   - View responses from your mentor
   - Check status (Pending/Responded)

## Project Structure

```
src/main/java/com/example/attendancemanagement/
├── config/              # Configuration classes (Security, Data Initialization)
├── controller/          # MVC Controllers
├── model/               # Entity classes (User, Student, Teacher, Attendance, Query)
├── repository/          # JPA Repositories
├── security/            # Security configuration
└── service/             # Business logic services

src/main/resources/
├── static/css/          # CSS stylesheets
├── templates/           # Thymeleaf templates
│   ├── teacher/         # Teacher portal views
│   └── student/         # Student portal views
└── application.properties # Application configuration
```

## Configuration Options

### Notification Settings

In `application.properties`:

```properties
# Enable/disable email notifications
app.notifications.email.enabled=true

# Enable/disable SMS notifications (requires SMS service integration)
app.notifications.sms.enabled=false
```

### Database Settings

The application uses Hibernate's `update` mode, which automatically creates/updates tables based on entities. For production, consider using `validate` or `none`.

## Security Features

- Role-based access control (ROLE_TEACHER, ROLE_STUDENT)
- Password encryption using BCrypt
- Secure session management
- CSRF protection (can be enabled in SecurityConfig)

## Future Enhancements

- SMS integration (Twilio, AWS SNS)
- Bulk attendance marking
- Attendance reports and analytics
- Calendar view for attendance
- Export attendance data to Excel/PDF
- Admin panel for user management
- Automated mentor assignment

## Troubleshooting

### Database Connection Issues
- Ensure MySQL is running
- Verify database credentials in `application.properties`
- Check if database exists: `CREATE DATABASE attendance_management;`

### Email Not Working
- Verify email credentials
- For Gmail, use App Password (not regular password)
- Check firewall/network settings
- Set `app.notifications.email.enabled=false` to disable

### Login Issues
- Use default credentials provided above
- Ensure user exists in database
- Check role assignment (ROLE_TEACHER or ROLE_STUDENT)

## License

This project is open source and available for educational purposes.

## Support

For issues or questions, please check the code comments or create an issue in the repository.


