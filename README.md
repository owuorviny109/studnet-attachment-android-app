**AttachME**
AttachME is a modern Android application designed to connect students seeking internships with companies offering opportunities. It leverages the power of Jetpack Compose for the UI, Firebase Authentication for user management, and Firestore for real-time data syncing, creating a seamless experience for both students and companies.

**Features**
User Authentication: Students and companies can sign up and log in using Firebase Authentication. Students can view internship opportunities, while companies can post and manage their internship listings.

**Post Internships:** Companies can easily create and post internship opportunities, including details like company name, position title, description, requirements, location, duration, available slots, and application deadline.

**Browse Opportunities:** Students can browse available internships in real-time, ordered by the most recently posted. They can also apply directly for positions of interest.

**Real-Time Updates:** Internship listings are automatically updated in real-time using Firestore. As companies post or modify opportunities, the changes are reflected immediately for all users.

**Clean UI:** Built with Jetpack Compose, the app provides a modern and fluid user interface optimized for both phone and tablet devices. The design is simple yet effective, ensuring a smooth user experience.

**Error Handling:** Proper error states are displayed to users in case of failures or loading issues, ensuring a user-friendly experience.

**Tech Stack**
Android: The app is built using the latest Android technologies, including Jetpack Compose for the user interface and ViewModel for managing UI-related data lifecycle-consciously.

****Firebase:**
Firebase Authentication for user sign-up, login, and account management.

Cloud Firestore for storing and managing internship opportunities in real-time.

Kotlin: The app is written in Kotlin, leveraging its modern, expressive syntax to handle both backend logic and UI components.

Dagger Hilt: Dependency injection is handled with Dagger Hilt for better scalability, cleaner code, and easy testing.

Coroutines & Flow: Kotlin Coroutines and Flow are used to manage background tasks and stream data, ensuring smooth and responsive performance even with large amounts of data.

 
