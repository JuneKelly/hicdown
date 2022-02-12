# hicdown

A fun little language, embedding a hiccup-like syntax in plain text.

Example:

```
Hello there, this is [:bold some strong text].

And this is another block, with a [:link {to=example.com} cool website].
```

Parsing:

``` clojure
(parse-string "Hello, this is [:bold some [:italic {id=foo} strong] text here].")
;; produces...
[:Document 
  [:Block 
    [:Text "H" "e" "l" "l" "o" "," " " "t" "h" "i" "s" " " "i" "s" " "]
    [:Segment [:tag ":bold"] 
              [:Text "s" "o" "m" "e" " "] 
      [:Segment [:tag ":italic"] 
                [:Attrs [:Pair [:key "id"] [:val "foo"]]] 
                [:Text "s" "t" "r" "o" "n" "g"]] 
      [:Text " " "t" "e" "x" "t" " " "h" "e" "r" "e"]] 
    [:Text "."]]]
```

Rendering to html:

``` clojure
(render-string "Hello, this is a [:a {href=example.com} link].\n\nAnd this is another block.")
;; produces
"<root><div>Hello, this is a <a href=example.com>link</a>.</div><div>And this is another block.</div></root>"
```

## The Language

Blocks are separated by blank lines:

```
This is a block.
It is quite nice.

And this is another block.
```

Segments are demarcated by square brackets, with a tag, and optional attributes and text content.

```
Consider this [:strong very heavy] text.

Here is a [:a {href="example.com"} link to a cool website], that you
might enjoy.
```

Attributes can be either key-value pairs, or single values. Values may be quoted:

```
[:h1 {id="some-heading" some-attribute} This is a heading]
```

Segments may be nested:

```
This is [:a {href="example.com"} a link to a [:strong cool] website].
```

Verbatim segments enclose text in `~~~` markers at the beginning and end of the segment:

```
Consider this nice code...

[:pre [:code {language=javascript} ~~~
const greet = (name) => {
  return `Hello, ${name}.`
}
~~~]]

Note, whitespace between the verbatim-markers and the text will be ignored.

The following text will be included verbatim: [:span ~~~ this is [:img just text], not an image ~~~].
```

If a block consists of just one segment, the segment is promoted to take the
place of the block (when rendering to html at least).


## TODO

- [x] Compile to HTML
- [x] Document-level attributes
- [x] Escape content of text? ("<" and so on?)
- [x] Tests for html rendering
- [ ] Native compilation with Graal?
- [ ] What to do with document-level attrs?
- [ ] Customize root element tag
- [ ] More CLI options
- [ ] Cleaner node names
- [ ] Tidy up the rendering code
- [ ] Should verbatim drop leading newlines before text begins?
- [ ] What would this lang look like without the block concept?
- [ ] Move away from 1-1 html mapping
  - [ ] Short-hand tags


## Installation

Don't. 

## Usage

Also don't. But if you must:

Parse a file:

``` bash
$ clojure -M:run-m parse < examples/basic.hd
```

Render a file (to html):

``` bash
$ clojure -M:run-m render < examples/basic.hd
```

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
