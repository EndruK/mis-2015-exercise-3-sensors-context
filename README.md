# Mobile Information Systems Excercise III

## Group:
- André Karge (110033)
- Kevin Lang  (110010)

## Views:
Upper View:
- plots the accelerometer data
- red = X
- green = Y
- blue = Z
- white = magnitude
Lower View:
- plots the FFT of the magnitude in yellow

## SeekBars:
Upper SeekBar:
- modify the sample rate (0 - 100.000)
Lower SeekBar:
- modify the FFT window size (2^3 - 2^9 values)

## Activity Classification
We decide to determine if the user is running or walking or relaxing.
For that we printed the average and the maximum frequency of the FFT result.
After that we made a little study how the values change during our three activities.
At the end we came up to use only the average frequency. When the user does nothing the average frequency is lower than 25, when he is running it is somewhere in between 25 and 31 and if he is running than the average frequency is above 31.
It is just a simplification and it could have been more accurate but for the three activities it is sufficient.

## Test Environment
The app was tested with:
- Nexus 4
- Samsung Galaxy S2
- the android emulator (not very effective because of sensor stuff)
