# hicdown

A fun little parser, embedding a hiccup-like syntax in plain text.

Example:

```
Hello there, this is [:bold some strong text].

And this is another block, with a [:link {:to example.com} cool website].
```

Parsing:

``` clojure
(parser "Hello, this is [:bold some [:italic {:id foo} strong] text here].")
[:Document [:Block "H" "e" "l" "l" "o" "," " " "t" "h" "i" "s" " " "i" "s" " " 
  [:Segment [:tag ":bold"] "s" "o" "m" "e" " " 
    [:Segment [:tag ":italic"] [:Attrs [:KVPair [:key ":id"] [:val "foo"]]] 
      " " "s" "t" "r" "o" "n" "g"] 
    " " "t" "e" "x" "t" 
    " " "h" "e" "r" "e"] "."]]
```

## Todo

- [X] Basics
- [x] Remove leading and trailing newlines from parse tree
- [x] Tests
- [x] Switch to capturing individual characters in text run
- [x] Get escape characters right
  - [x] Should web hide these nodes in the tree?
- [x] Newline escapes
- [x] Escape the backslash character
- [ ] Remove extra spaces from Text runs, make it consistent
- [ ] String values in attribute map
- [ ] Comments
- [ ] Look into how we can use instaparse metadata and transforms
- [ ] Verbatim segments


## Installation

Don't. 

## Usage

FIXME: explanation

Run the project directly, via `:exec-fn`:

    $ clojure -X:run-x
    Hello, Clojure!

Run the project, overriding the name to be greeted:

    $ clojure -X:run-x :name '"Someone"'
    Hello, Someone!

Run the project directly, via `:main-opts` (`-m wtf.halt.hicdown`):

    $ clojure -M:run-m
    Hello, World!

Run the project, overriding the name to be greeted:

    $ clojure -M:run-m Via-Main
    Hello, Via-Main!

Run the project's tests (they'll fail until you edit them):

    $ clojure -T:build test

Run the project's CI pipeline and build an uberjar (this will fail until you edit the tests to pass):

    $ clojure -T:build ci

This will produce an updated `pom.xml` file with synchronized dependencies inside the `META-INF`
directory inside `target/classes` and the uberjar in `target`. You can update the version (and SCM tag)
information in generated `pom.xml` by updating `build.clj`.

If you don't want the `pom.xml` file in your project, you can remove it. The `ci` task will
still generate a minimal `pom.xml` as part of the `uber` task, unless you remove `version`
from `build.clj`.

Run that uberjar:

    $ java -jar target/hicdown-0.1.0-SNAPSHOT.jar

If you remove `version` from `build.clj`, the uberjar will become `target/hicdown-standalone.jar`.

## Options

FIXME: listing of options this app accepts.

## Examples

...

### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2022 Junebug

_EPLv1.0 is just the default for projects generated by `clj-new`: you are not_
_required to open source this project, nor are you required to use EPLv1.0!_
_Feel free to remove or change the `LICENSE` file and remove or update this_
_section of the `README.md` file!_

Distributed under the Eclipse Public License version 1.0.
