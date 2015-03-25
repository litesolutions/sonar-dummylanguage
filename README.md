## Read me first

The license of this project is LGPLv3 or later. See the `LICENSE` file for more
details.

## What this is

This is an example of a complete but minimal language plugin for SonarQube. It
has been tested on version 5.0.1 and 5.1-RC2 of SonarQube.

The language is really a dummy language... It parses a file with basic
mathematic expressions like this:

```
#
# Operation 1...
#
1 + 3
#
# Operation 2...
#
4 / 1
```

Only integers are allowed, no parens, etc etc.

This plugin uses
[sonar-sslr-grappa](https://github.com/litesolutions/sonar-sslr-grappa), meaning
that the full language is parsed by one and only one channel.

## Building it...

At the moment, the above package is not published on maven! You will therefore
need to clone it and type the following at the command line:

```
# Unix users...
./gradlew clean test install
# Windows users...
gradlew.bat clean test install
```

## Testing it...

There is a module using the SSLR toolkit, which you can use to test your inputs.
Running this module also generates a trace file for your grappa parser; note
that for now it is **hardcoded** to be generate in `/tmp/trace.zip`.

You can easily test only parts of your parser and grammar by modifying this file
and changing the parser rule and grammar key (provided that the parser rule is
`public`!).

## TODO

More documentation!

