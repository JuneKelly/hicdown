(ns wtf.halt.segment-test
  (:require  [clojure.test :as t]
             [wtf.halt.hicdown-test-utils :refer [test-parse s]]))

(t/deftest segments
  (t/testing "Segments"

    (test-parse
     "segments/empty.hd"
     `[:Document
       [:Block
        [:Text "Test "]
        [:Segment [:tag ":a"]]
        [:Text " foo."]]])

    (test-parse
     "segments/empty-with-attrs.hd"
     `[:Document
       [:Block
        [:Text "Test "]
        [:Segment [:tag ":a"]
         [:Attrs [:Pair [:key "x"] [:val "y"]] [:Atom "z"]]]
        [:Text " foo."]]])

    (test-parse
     "segments/with-content-text.hd"
     `[:Document
       [:Block
        [:Text "Test "]
        [:Segment [:tag ":a"]
         [:Text "foo"]]
        [:Text " bar."]]])

    (test-parse
     "segments/with-content-text-and-attrs.hd"
     `[:Document
       [:Block
        [:Text "Test "]
        [:Segment [:tag ":a"]
         [:Attrs [:Pair [:key "x"] [:val "y"]]]
         [:Text "foo"]]
        [:Text " bar."]]])

    (test-parse
     "segments/spanning-newline.hd"
     `[:Document
       [:Block
        [:Text "Test "]
        [:Segment [:tag ":a"]
         [:Attrs [:Pair [:key "x"] [:val "y"]]]
         [:Text "foo\nbar baz"]]
        [:Text " quux."]]])

    (test-parse
     "segments/with-escapes.hd"
     `[:Document
       [:Block
        [:Text
         "Test \\[ something \\] here."]]
       [:Block
        [:Text "And \\{ something else \\} here."]]])

    (test-parse
     "segments/with-escapes-and-segments.hd"
     `[:Document
       [:Block
        [:Text "Test "]
        [:Segment [:tag ":a"]
         [:Attrs [:Pair [:key "x"] [:val "y"]]]
         [:Text "foo"]]
        [:Text " bar \\[ baz \\]."]]])

    (test-parse
     "segments/newline-escape.hd"
     `[:Document
       [:Block
        [:Text "Test one two\n\\\nthree four."]]])

    (test-parse
     "segments/backslash-escape.hd"
     `[:Document
       [:Block
        [:Text "Test \\\\"]
        [:Segment [:tag ":a"]
         [:Text "foo"]]
        [:Text " bar."]]])

    (test-parse
     "segments/nested-segments.hd"
     `[:Document
       [:Block
        [:Text "Test "]
        [:Segment [:tag ":a"]
         [:Segment [:tag ":b"]
          [:Attrs [:Pair [:key "x"] [:val "y"]]]
          [:Segment [:tag ":c"]
           [:Text "foo"]]]]
        [:Text " bar."]]])

    ;; (test-parse
    ;;  "segments/spanning-double-newline.hd"
    ;;  `[:Document
    ;;    [:Block
    ;;     [:Text "Test "]
    ;;     [:Segment [:tag ":a"]
    ;;      [:Attrs [:Pair [:key ":x"] [:val "y"]]]
    ;;      [:Text "foo\n\n\n"
    ;;       "bar baz"]]
    ;;     [:Text " quux."]]])
    ))
