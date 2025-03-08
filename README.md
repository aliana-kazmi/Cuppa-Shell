# Cuppa Shell 

Cuppa Shell is a custom shell created in Java which was created according to the Codecrafters challenge. It provides a REPL (Read-Eval-Print Loop) environment, supports built-in commands, executes external programs, and includes essential shell features such as navigation commands and quoting support.

## Features

- **Interactive Shell Environment**

  - Implements a Read-Eval-Print Loop (REPL) with a custom prompt.

- **Command Handling**

  - Supports built-in commands such as:
    - `exit 0`  - Exits the shell.
    - `echo` - Prints arguments to the console.
    - `type` - Identifies built-in commands and executable files.

- **Program Execution**

  - Executes external programs from the shell environment.

- **Navigation Commands**

  - `pwd` - Displays the current working directory.
  - `cd` - Changes the current directory (supports absolute, relative, and home directory navigation).

- **Quoting Support**

  - Supports single-quoted strings to handle spaces and special characters in commands.

## Installation

1. Clone this repository:
   ```sh
   git clone https://github.com/aliana-kazmi/Cuppa-Shell.git
   ```
2. Navigate to the project directory:
   ```sh
   cd cuppa-shell
   ```
3. Compile and run:
   ```sh
   ./your_program.sh
   ```

## Usage

Once the shell is running, you can enter commands just like in a standard shell. Here are a few examples:

```sh
cuppa> echo Hello, World!
Hello, World!

cuppa> echo 'Welcome to Cuppaaaa     Shell'
Welcome to Cuppaaaa     Shell

cuppa> pwd
/home/user

cuppa> cd /usr/local
cuppa> pwd
/usr/local

cuppa> ../
cuppa>pwd
/usr

cuppa> type echo
echo is a built-in command

cuppa> type cd hello
cd is a built-in command
hello: not found

cuppa> exit 0
```

## Contributing

Contributions are welcome! Feel free to fork the repository, submit issues, or open a pull request with improvements.
