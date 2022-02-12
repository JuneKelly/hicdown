(ns wtf.halt.html-test
  (:require  [clojure.test :as t]
             [wtf.halt.hicdown-test-utils :refer [test-render]]))

(t/deftest simple-html
  (t/testing "Simple document"
    (test-render
     "simple/one-block.hd"
     (str "<root>"
          "<div>"
          "Hello this is a simple\nblock of text"
          "</div>"
          "</root>")))

  (t/testing "More complex document"
    (test-render
     "verbatim/code.hd"
     (str "<root>"
          "<div>This is some code:</div>\n"
          "<code language=javascript>"
          "const greet = (name) =&gt; {\n"
          "  return `Hello, ${name}.`\n"
          "}"
          "</code>\n"
          "<div>And this is text again.</div>"
          "</root>")))

  (t/testing "Even more complex"
    (test-render
     "render/example.hd"
     (str "<root author=\"Alice\">"
          "\n"
          "<h1 id=nice-heading>Example document</h1>"
          "\n"
          "<div>This is an <i>example</i> document.</div>"
          "\n"
          "<p>This is an explicit paragraph."
          "\n"
          "  Here is a <a href=\"example.com\">link</a> to"
          "\n"
          "  a cool website.\n</p>"
          "\n"
          "\n"
          "<pre><code language=\"javascript\">"
          "\n"
          "const x = 1;"
          "\n"
          "console.log(\"number is \", x)"
          "</code></pre>"
          "\n"
          "\n"
          "<ul>"
          "<li>One</li>\n  "
          "<li>Two</li>\n  "
          "<li><ul><li>Three</li></ul></li></ul>\n"
          "</root>"))))
