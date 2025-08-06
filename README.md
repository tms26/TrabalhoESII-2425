# ESIIM

## Introduction
Our application designed to calculate the Product Carbon Footprint (PCF) of a product based on provided production and emissions data. It parses input files, models product and emission data, calculates the PCF, and exports the results in a structured format.

## Features
- Parse CSV files with product and emissions data.
- Model product and emissions entities based on input data.
- Calculate the Product Carbon Footprint (PCF).
- Export results to a CSV file.

## Prerequisites
To run the application, ensure the following are installed on your system:
- **Java**: Version 8 or higher.
- **Gradle**: Version 7.0 or higher.

## Project Structure
The project follows a modular structure, with the main components:
- **Parser**: Parses input CSV files.
- **Modeler**: Models product and emission entities.
- **Calculator**: Calculates the PCF.
- **Exporter**: Exports results to CSV.

## Installation
1. Clone the repository:
   ```bash
   git clone http://gitlab.estg.ipp.pt/grupo-m-esii-24-25/esiim.git
   ```
2. Navigate to the project directory:
   ```bash
   cd esiim
   ```
3. Build the project using Gradle:
   ```bash
   ./gradlew build
   ```

## Usage
1. Prepare your input files:
   - **Product data file**: A CSV file containing product information (e.g., `shoe_production.csv`).
   - **Emissions data file**: A CSV file containing emissions data (e.g., `emissions.csv`).

2. Run the application:
   The default input files are located in the `app/src/main/resources/` directory.

   - To specify custom file paths, update the `filePath` and `emissionsFilePath` variables in the `App` class.

3. Output:
   - The application calculates the Product Carbon Footprint (PCF) and exports the results to a CSV file in the `root` directory.

## Input File Format
### Product Data File (CSV)
The product data file should contain the following columns:
- Name
- Country
- Weight
- Unit
- Process Type
- Product Flow Name
- Flow Name
- Unit
- Category
- Quantity
- Tag
- Type
- Origin Country

### Emissions Data File (CSV)
The emissions data file should contain the following columns:
- Name
- Category
- Unit
- Quantity
- Emission Factor

## Example Workflow
1. **Input Files**: Place the input files in `app/src/main/resources/`.
2. **Run the Application**: Execute the application as described above.
3. **Output**: The calculated PCF and related data will be available in a CSV file in the `root` directory.

## Error Handling
The application provides clear error messages for invalid data or parsing issues. Common exceptions include:
- `InvalidDataException`: Triggered by malformed or missing data in the input files.
- `InvalidParseDataException`: Raised during the modeling process if input data is invalid.
- `InvalidCalculationException`: Raised during PCF calculation errors.
- `InvalidProductException`: Raised during the exporter fase of a product. 


## Authors
This project was developed by the ESIIM development team. For any inquiries or support, please contact the repository owners.

## Acknowledgments
Special thanks to Professor Cristóvão and Professor Ricardo for their valuable input and support.
