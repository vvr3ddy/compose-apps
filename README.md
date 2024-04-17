# Jetpack Compose Todo App

## Overview

This Todo app is a simple yet powerful task management tool developed using Jetpack Compose, the modern toolkit for building native UI on Android. This project serves as a practical demonstration of modern Android development practices with a focus on state management, UI design, and data handling using Jetpack Compose.

## Features

- **Add Tasks**: Users can add tasks to their todo list.
- **Toggle Tasks**: Tasks can be marked as completed or reverted to uncompleted.
- **Delete Tasks**: Users can remove tasks from the list.

## Core Concepts Incorporated

### Jetpack Compose
- **Composable Functions**: Building UI components in a declarative style using composable functions that can be composed into complex UIs.
- **Modifiers**: Used to modify the appearance or behavior of composables.

### State Management
- **ViewModel**: Holds and manages UI-related data in a lifecycle-conscious way, allowing data to survive configuration changes such as screen rotations.
- **StateFlow**: An observable data holder that emits updates to the UI upon data state changes.
- **MutableStateFlow**: A mutable version of StateFlow used in ViewModel to handle the updating of state.

### List Management
- **LazyColumn**: An efficient way to display lists of items that only renders the visible items on screen, improving performance over traditional RecyclerViews.
- **Unique Identifiers**: Using unique IDs to manage list operations such as updates and deletions more effectively.

### Asynchronous Programming
- **Coroutines**: Utilized within ViewModel to manage asynchronous operations seamlessly.

## Getting Started

### Prerequisites
- Android Studio Arctic Fox | 2020.3.1 or newer
- Minimum SDK API level: 21

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/vvr3ddy/compose-apps.git -b week01
   ```
2. Open the project in Android Studio.
3. Build the project by navigating to `Build > Make Project`.
4. Run the application on an emulator or a physical device.

### Usage
- To add a task, enter the task name in the text field and press 'Enter' or click the 'Add' button.
- Toggle the completion status of a task by checking or unchecking the box next to the task.
- Remove a task by clicking on the trash can icon next to it.

## License
Distributed under the MIT License. See `LICENSE` for more information.
