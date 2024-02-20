# BMPImageHandler
The BMPImageHandler program is designed to manipulate BMP images by changing their colors and dimensions. It offers functionality to change the color scheme of images to red, sepia, blue, and green. Additionally, it can rotate images vertically and horizontally by 180 degrees and resize images to make them thinner or flatter.

## Usage
To use the BMPImageHandler program, follow these instructions:

1. Clone the repository to your local machine:
```
git clone https://github.com/yourusername/BMPImageHandler.git
```
2. Compile the program using Java:
```
javac BMPImageHandler.java
```
3. Run the program using the following command:
```
java BMPImageHandler <flag> <file>
```
- <flag>: Specify the operation you want to perform on the BMP image. Available flags are:
  -basics: Change the color scheme of the image.
  -rotate: Rotate the image by 180 degrees (both vertically and horizontally).
  -resize: Resize the image to make it thinner or flatter.
  -all: Perform all operations (change color, rotate, and resize) on the image.
<file>: Provide the path to the BMP image file you want to manipulate.

## Example
```
java BMPImageHandler -all mk1.bmp
```
This command will change the color scheme of the image, rotate it by 180 degrees (both vertically and horizontally), and resize it according to the default settings.

## Requirements
The program requires Java to be installed on your system.

## Tests
The tests folder contains test cases to validate the functionality of the BMPImageHandler program. You can run these tests to ensure that the program behaves as expected in different scenarios.

## Contributions
Contributions to the BMPImageHandler program are welcome! If you have suggestions for improvements or bug fixes, please feel free to submit a pull request or open an issue on GitHub.
