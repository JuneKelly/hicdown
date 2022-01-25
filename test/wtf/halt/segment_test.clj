(ns wtf.halt.segment-test
  (:require  [clojure.test :as t]
             [wtf.halt.hicdown-test-utils :refer [test-parse s]]))

(t/deftest segments
  (t/testing "Segments"

    (test-parse
     "segments/empty.hd"
     `[:Document
       [:Block
        [:Text ~@(s "Test ")]
        [:Segment [:tag ":a"]]
        [:Text ~@(s " foo.")]]])

    (test-parse
     "segments/empty-with-attrs.hd"
     `[:Document
       [:Block
        [:Text ~@(s "Test ")]
        [:Segment [:tag ":a"]
         [:Attrs [:Pair [:key ":x"] [:val "y"]]]]
        [:Text ~@(s " foo.")]]])

    (test-parse
     "segments/with-content-text.hd"
     `[:Document
       [:Block
        [:Text ~@(s "Test ")]
        [:Segment [:tag ":a"]
         [:Text ~@(s "foo")]]
        [:Text ~@(s " bar.")]]])

    (test-parse
     "segments/with-content-text-and-attrs.hd"
     `[:Document
       [:Block
        [:Text ~@(s "Test ")]
        [:Segment [:tag ":a"]
         [:Attrs [:Pair [:key ":x"] [:val "y"]]]
         [:Text ~@(s " foo")]] ;; Note the extra space here, should be parsed out?
        [:Text ~@(s " bar.")]]])

    (test-parse
     "segments/spanning-newline.hd"
     `[:Document
       [:Block
        [:Text ~@(s "Test ")]
        [:Segment [:tag ":a"]
         [:Attrs [:Pair [:key ":x"] [:val "y"]]]
         [:Text ~@(s " foo\n")
          ~@(s "bar baz")]]
        [:Text ~@(s " quux.")]]])

    (test-parse
     "segments/with-escapes.hd"
     `[:Document
       [:Block
        [:Text
         ~@(s "Test ")
         [:esc "\\["]
         ~@(s " something ")
         [:esc "\\]"]
         ~@(s " here.")]]
       [:Block
        [:Text ~@(s "And ")
         [:esc "\\{"]
         ~@(s " something else ")
         [:esc "\\}"]
         ~@(s " here.")]]])

    (test-parse
     "segments/with-escapes-and-segments.hd"
     `[:Document
       [:Block
        [:Text ~@(s "Test ")]
        [:Segment [:tag ":a"]
         [:Attrs [:Pair [:key ":x"] [:val "y"]]]
         [:Text ~@(s " foo")]]
        [:Text ~@(s " bar ")
         [:esc "\\["]
         ~@(s " baz ")
         [:esc "\\]"]
         ~@(s ".")]]])

    (test-parse
     "segments/newline-escape.hd"
     `[:Document
       [:Block
        [:Text ~@(s "Test one two\n")
         [:esc "\\\n"]
         ~@(s "three four.")]]])

    (test-parse
     "segments/backslash-escape.hd"
     `[:Document
       [:Block
        [:Text ~@(s "Test ")
         [:esc "\\\\"]]
        [:Segment [:tag ":a"]
         [:Text ~@(s "foo")]]
        [:Text ~@(s " bar.")]]])

    (test-parse
     "segments/nested-segments.hd"
     `[:Document
       [:Block
        [:Text ~@(s "Test ")]
        [:Segment [:tag ":a"]
         [:Segment [:tag ":b"]
          [:Attrs [:Pair [:key ":x"] [:val "y"]]]
          [:Text " "]
          [:Segment [:tag ":c"]
           [:Text ~@(s "foo")]]]]
        [:Text ~@(s " bar.")]]])

    (test-parse
     "segments/spanning-double-newline.hd"
     `[:Document
       [:Block
        [:Text ~@(s "Test ")]
        [:Segment [:tag ":a"]
         [:Attrs [:Pair [:key ":x"] [:val "y"]]]
         [:Text ~@(s " foo\n\n\n")
          ~@(s "bar baz")]]
        [:Text ~@(s " quux.")]]])))
