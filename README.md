# Trade Report 


This repository contains a program that generates a report based on the data contained within the file.

## Details 

- A work week starts Monday and ends Friday, unless the currency of the trade is AED or SAR, where the work week starts Sunday and ends Thursday. No other holidays to be taken into account.
- A trade can only be settled on a working day
- If an instructed settlement date falls on a weekend, then the settlement date should be changed to the next working day
- USD amount of a trade = Price per unit * Units * Agreed Fx

## Requirements
Create a report that shows:
- Amount in USD settled incoming everyday
- Amount in USD settled outgoing everyday
- Ranking of entities based on incoming and outgoing amount. Eg: If entity foo instructs the highest amount for a buy instruction, then foo is rank 1 for outgoing.

## Glossary of terms:

- Instruction: An instruction to buy or sell
- Entity: A financial entity whose shares are to be bought or sold
- Instruction Date: Date on which the instruction was sent by various clients
- Settlement Date : The date on which the client wished for the instruction to be settled with respect to Instruction Date
- Buy/Sell flag:
    - B –Buy – outgoing 
    - S –Sell – incoming
- AgreedFx: is the foreign exchange rate with respect to USD that was agreed
- Units: Number of shares to be bought or sold

## Assumptions 
 - Data incoming is in JSON format
 - The JSON is supplied in file format.
 
## Design
 - It was decided that the Instructions would be supplied in a JSON format file.  This file temporarily sits on the classpath for the moment.
 - Once the data had been loaded in, then a few adjustments had to be made to the data
    1. To adjust the settlement data (if required)
    2. Calculate the Trade Amount.
This was performed using a Consumer function over the Collection of Instructions. The Consumer class accepts the model type and then perfoms the adjustments required. 
- Once the data had been adjusted, it was mapped out using a lambda function to split the data up by Buy and Sell.  The data was then further mapped by Settlement Date.
- The 3 required reports were then produced and their details are outputted to the console.
  
## Dependencies
The code base requires to dependencies:
- Java 1.8
- [Project Lombok](https://projectlombok.org/)
- [Jackson Databind](https://github.com/FasterXML/jackson-databind)

Both are marked as dependencies with Maven and when building, the should be downloaded from the repository.

To edit the code in an IDE Project Lombok is required.  For more details on Project Lombok see [here](https://projectlombok.org/).
  
## Building
1. Clone the repository.
2. Move into the directory created from the clone
3. From the command line run : ```mvn clean install```

## Running
To run the code to see the results, execure ```mvn exex:java``` on the command line.  The report will be shown on the screen.
Alternativley, the jar file created during the build process can be executed by running:
```java -jar TradeReport-0.1-SNAPSHOT-jar-with-dependencies.jar``` 
from the command line.  The jar can be found the  `target` directory.
