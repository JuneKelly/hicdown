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
         "Test "]
        [:Segment [:tag ":a"]
         [:Attrs [:Pair [:key "x"] [:val "y"]]]
         [:VerbatimText
          "%%%"
          ~@(s " hello ")
          "%%%"]]]])

    (test-parse
     "verbatim/not-segment.hd"
     `[:Document
       [:Block
        [:Text
         "Test "]
        [:Segment [:tag ":a"]
         [:Attrs [:Pair [:key "x"] [:val "y"]]]
         [:VerbatimText
          "%%%"
          ~@(s " hello [:foo {g=h} not a segment], just text. ")
          "%%%"]]
        [:Text ", bar."]]])

    (test-parse
     "verbatim/code.hd"
     `[:Document
       [:Block
        [:Text
         "This is some code:"]]
       [:Block
        [:Segment [:tag ":code"]
         [:Attrs [:Pair [:key "language"] [:val "javascript"]]]
         [:VerbatimText
          "%%%"
          ~@(s "\nconst greet = (name) => {\n  return `Hello, ${name}.`\n}\n")
          "%%%"]]]
       [:Block
        [:Text "And this is text again."]]])

    (test-parse
     "verbatim/with-embedded-end-markers.hd"
     `[:Document
       [:Block
        [:Text
         "This is verbatim text:"]]
       [:Block
        [:Segment [:tag ":a"]
         [:VerbatimText
          "%%%"
          ~@(s "\nDo you like percent signs? %%% like these ones?\n")
          "%%%"]]]
       [:Block
        [:Text "Back to text."]]])

    (test-parse
     "verbatim/with-double-new-lines.hd"
     `[:Document
       [:Block
        [:Text
         "This is verbatim text:"]]
       [:Block
        [:Segment [:tag ":a"]
         [:VerbatimText
          "%%%"
          ~@(s "\nTest one\n\ntwo three\nfour\n\n\n\nfive.\n")
          "%%%"]]]
       [:Block
        [:Text "Back to text."]]])

    (test-parse
     "verbatim/extra-end-markers.hd"
     `[:Document
       [:Block
        [:Text
         "Test: "]
        [:Segment [:tag ":a"]
         [:VerbatimText
          "%%%"
          ~@(s "%% hello %%")
          "%%%"]]
        [:Text " foo."]]])))
