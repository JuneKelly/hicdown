(ns wtf.halt.verbatim-test
  (:require  [clojure.test :as t]
             [wtf.halt.hicdown-test-utils :refer [test-parse s]]))

(t/deftest verbatim-segments
  (t/testing "Verbatim segments"
    (test-parse
     "verbatim/simple.hd"
     `[:Document
       [:Block
        [:Text
         ~@(s "Test ")]
        [:Segment [:tag ":a"]
         [:Attrs [:Pair [:key "x"] [:val "y"]]]
         [:VerbatimText
          [:VerbatimBegin "~~~"]
          ~@(s "hello")
          [:VerbatimEnd "~~~"]]]]])

    (test-parse
     "verbatim/not-segment.hd"
     `[:Document
       [:Block
        [:Text
         ~@(s "Test ")]
        [:Segment [:tag ":a"]
         [:Attrs [:Pair [:key "x"] [:val "y"]]]
         [:VerbatimText
          [:VerbatimBegin "~~~"]
          ~@(s "hello [:foo {g=h} not a segment], just text.")
          [:VerbatimEnd "~~~"]]]
        [:Text ~@(s ", bar.")]]])

    (test-parse
     "verbatim/code.hd"
     `[:Document
       [:Block
        [:Text
         ~@(s "This is some code:")]]
       [:Block
        [:Segment [:tag ":code"]
         [:Attrs [:Pair [:key "language"] [:val "javascript"]]]
         [:VerbatimText
          [:VerbatimBegin "~~~"]
          ~@(s "const greet = (name) => {\n  return `Hello, ${name}.`\n}")
          [:VerbatimEnd "~~~"]]]]
       [:Block
        [:Text ~@(s "And this is text again.")]]])

    (test-parse
     "verbatim/with-embedded-end-markers.hd"
     `[:Document
       [:Block
        [:Text
         ~@(s "This is verbatim text:")]]
       [:Block
        [:Segment [:tag ":a"]
         [:VerbatimText
          [:VerbatimBegin "~~~"]
          ~@(s "Do you like tildes? ~~~ like these ones?")
          [:VerbatimEnd "~~~"]]]]
       [:Block
        [:Text ~@(s "Back to text.")]]])

    (test-parse
     "verbatim/with-double-new-lines.hd"
     `[:Document
       [:Block
        [:Text
         ~@(s "This is verbatim text:")]]
       [:Block
        [:Segment [:tag ":a"]
         [:VerbatimText
          [:VerbatimBegin "~~~"]
          ~@(s "Test one\n\ntwo three\nfour\n\n\n\nfive.")
          [:VerbatimEnd "~~~"]]]]
       [:Block
        [:Text ~@(s "Back to text.")]]])

    (test-parse
     "verbatim/extra-end-markers.hd"
     `[:Document
       [:Block
        [:Text
         ~@(s "Test: ")]
        [:Segment [:tag ":a"]
         [:VerbatimText
          [:VerbatimBegin "~~~"]
          [:VerbatimNestedMark "~"]
          [:VerbatimNestedMark "~"]
          ~@(s "hello")
          [:VerbatimNestedMark "~"]
          [:VerbatimNestedMark "~"]
          [:VerbatimEnd "~~~"]]]
        [:Text ~@(s " foo.")]]])))
