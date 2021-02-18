# FinanceLingo
An Interpreted DSL that enables FinancialAnalytics.

# Objective
The primary motivation or design goal is to provide a simple and yet rich expressive language for the finance domain.
For doing some basic analytics, it should ***not be required to write a detailed Java or C++ program***. Simply put, it acts like a scratch pad or a command line calculator. This is still **work in progress**. Details on the work items have been **highlighted in later sections.**

As of today, the DSL operates in an ***interpreted mode*** and provides very basic bond analytic constructs. 
It is not a complete **programming langauge** as it does not allow to define functions, support for control flow or decision making constructs. 

However, it does provide the following: 
*  basic ``variable assignments``
* ``arithmetic operators``
* `In-Built functions` that can be called

# Sample DSL Interpreter session
``` 
 Financial DSL interpreter version 1.0:
 > //Specify Instrument Contract details
 currency = USD
 matDate = 20151223
accDate = 20101222
int tenor = 6
double rate = .089
int settlementDays = 3
int notional = 100
daycount = actual_actual_icma
busdayconv = Following
yieldconv = us_street
issuer = us_govt
instrId = createBondInstrument(currency,matDate, accDate, tenor ,rate,settlementDays,notional,daycount, busdayconv, yieldconv,issuer)

printCashFlows(instrId)

// A Trade  date in between coupons
trDate = 20110223
bondContract = createBondContract(instrId,trDate)
print(bondContract)

// Calculate AccruedInterest
double ai =	calcAI(bondContract)
print(ai)

// Calculate Yield from Clean Price 
double cleanPrice   = 2.5 
double yield 	    = calcYieldFromCleanPrice(bondContract,cleanPrice) 
print (yield)

// Calculate dirty price
double dirtyPrice  = cleanPrice + ai/notional
print(dirtyPrice)

// Calculate Clean Price from Yield 
double yield  = 0.04 
double price  = calcCleanPriceFromYield(bondContract,yield) 

print (price)
```
# Usage
  Download the Zip. Unzip it on a local drive. Change to the folder ***distribution/Linux***.
  Execute the following script --> ***finInterpStart.sh*** to launch the interpreter.
  
# Planned Work Items
* Provide a Maven based build system, that allows one to build the binaries from the Source code.
* Add suitable test code snippets. 
* Code optimizations and more robust error handling.
* Windows based batch file.

# Phase 2 Work Items
* Provide support for finanical domain specfic constructs/operators and a supporting type system.
* Provide Risk measure calculations for Bonds.
* Provide Analytics and Risk calculations for Derivative and other asset classes.
