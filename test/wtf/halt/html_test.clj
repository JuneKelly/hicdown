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
          "</root>"))))
