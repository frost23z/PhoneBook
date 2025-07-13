# 📱 PhoneBook

A simple phonebook application built with JavaFX

![JavaFX](https://img.shields.io/badge/JavaFX-21.0.3-blue)
![Java](https://img.shields.io/badge/Java-17-orange)
![SQLite](https://img.shields.io/badge/SQLite-Database-green)
![Gradle](https://img.shields.io/badge/Gradle-8.7-brightgreen)

## ✨ Features

### 🔐 **User Authentication**

- Secure login and registration system
- User credential storage in SQLite database
- Default admin account for quick access
- Session management

### 📊 **Contact Management**

- **Add Contacts**: Create new contacts with name, phone, email, and address
- **Edit Contacts**: Update existing contact information
- **Delete Contacts**: Remove unwanted contacts with confirmation
- **Search Functionality**: Real-time search across name, phone, and email fields
- **Contact Count**: Live display of total contacts

### 🎨 **Modern UI Design**

- Beautiful gradient backgrounds and modern styling
- Responsive design with hover effects and animations
- Professional table layout with sorting capabilities
- FontAwesome icons for enhanced visual appeal
- Clean, intuitive user interface

### 💾 **Data Persistence**

- SQLite database for reliable data storage
- Automatic database initialization
- Contact data survives application restarts
- User management with encrypted storage

## 🚀 Quick Start

### Prerequisites

- Java 17 or higher
- Git (for cloning the repository)

### Installation & Running

1. **Clone the repository**
   ```bash
   git clone https://github.com/frost23z/PhoneBook.git
   cd PhoneBook
   ```

2. **Run the application**
   ```bash
   ./gradlew run
   ```

   On Windows:
   ```cmd
   gradlew.bat run
   ```

## 🎮 Usage Guide

### First Time Setup

1. Launch the application
2. You'll see the login screen with a beautiful gradient design
3. Use default credentials: `admin` / `admin` or you can register a new account
4. Or click "Register" to create a new account

### Managing Contacts

#### Adding a Contact

1. Click the "Add Contact" button (green button with + icon)
2. Fill in the contact details:
    - **Name**: Required field
    - **Phone**: Required field
    - **Email**: Optional
    - **Address**: Optional
3. Click "OK" to save

#### Editing a Contact

1. Select a contact from the table
2. Click the "Edit" button (blue button with edit icon)
3. Modify the desired information
4. Click "OK" to save changes

#### Deleting a Contact

1. Select a contact from the table
2. Click the "Delete" button (red button with trash icon)
3. Confirm the deletion in the popup dialog

#### Searching Contacts

- Use the search bar at the top of the main window
- Search works across name, phone, and email fields
- Results update in real-time as you type

## 🎨 UI Features

### Authentication Screen

- **Split Layout**: Info panel on the left, login form on the right
- **Dynamic Forms**: Seamless switching between login and registration
- **Validation**: Real-time error messages for invalid inputs
- **Modern Styling**: Gradient backgrounds with smooth animations

### Main Application

- **Header Section**: App title, search bar, and logout button
- **Action Toolbar**: Add, edit, and delete buttons with contact count
- **Data Table**: Sortable columns with professional styling
- **Responsive Design**: Hover effects and smooth transitions

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 👨‍💻 Author

**frost23z**

- GitHub: [@frost23z](https://github.com/frost23z)

## 🙏 Acknowledgments

- JavaFX community for excellent documentation
- FontAwesome for beautiful icons
- SQLite team for the reliable database engine

⭐ If you found this project helpful, please give it a star on GitHub!