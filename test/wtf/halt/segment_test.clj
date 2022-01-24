(ns wtf.halt.segment-test
  (:require  [clojure.test :as t]
             [wtf.halt.hicdown-test-utils :refer [test-parse s]]))

(t/deftest segments
  (t/testing "Segments"

    (test-parse
     "segments/empty.hd"
     `[:Document
       [:Block
        ~@(s "Test ")
        [:Segment [:tag ":a"]]
        ~@(s " foo.\n")]])

    (test-parse
     "segments/empty-with-attrs.hd"
     `[:Document
       [:Block
        ~@(s "Test ")
        [:Segment [:tag ":a"]
         [:Attrs [:KVPair [:key ":x"] [:val "y"]]]]
        ~@(s " foo.\n")]])

    (test-parse
     "segments/with-content-text.hd"
     `[:Document
       [:Block
        ~@(s "Test ")
        [:Segment [:tag ":a"]
         ~@(s "foo")]
        ~@(s " bar.\n")]])

    (test-parse
     "segments/with-content-text-and-attrs.hd"
     `[:Document
       [:Block
        ~@(s "Test ")
        [:Segment [:tag ":a"]
         [:Attrs [:KVPair [:key ":x"] [:val "y"]]]
         ~@(s " foo")] ;; Note the extra space here, should be parsed out?
        ~@(s " bar.\n")]])

    (test-parse
     "segments/spanning-newline.hd"
     `[:Document
       [:Block
        ~@(s "Test ")
        [:Segment [:tag ":a"]
         [:Attrs [:KVPair [:key ":x"] [:val "y"]]]
         ~@(s " foo\n")
         ~@(s "bar baz")]
        ~@(s " quux.\n")]])

    (test-parse
     "segments/with-escapes.hd"
     `[:Document
       [:Block
        ~@(s "Test ")
        [:esc "\\["]
        ~@(s " something ")
        [:esc "\\]"]
        ~@(s " here.")]
       [:Block
        ~@(s "And ")
        [:esc "\\{"]
        ~@(s " something else ")
        [:esc "\\}"]
        ~@(s " here.\n")]])

    (test-parse
     "segments/with-escapes-and-segments.hd"
     `[:Document
       [:Block
        ~@(s "Test ")
        [:Segment [:tag ":a"]
         [:Attrs [:KVPair [:key ":x"] [:val "y"]]]
         ~@(s " foo")]
        ~@(s " bar ")
        [:esc "\\["]
        ~@(s " baz ")
        [:esc "\\]"]
        ~@(s ".\n")]])

    (test-parse
     "segments/newline-escape.hd"
     `[:Document
       [:Block
        ~@(s "Test one two\n")
        [:esc "\\\n"]
        ~@(s "three four.\n")]])

    (test-parse
     "segments/backslash-escape.hd"
     `[:Document
       [:Block
        ~@(s "Test ")
        [:esc "\\\\"]
        [:Segment [:tag ":a"]
         ~@(s "foo")]
        ~@(s " bar.\n")]])

    (test-parse
     "segments/nested-segments.hd"
     `[:Document
       [:Block
        ~@(s "Test ")
        [:Segment [:tag ":a"]
         [:Segment [:tag ":b"]
          [:Attrs [:KVPair [:key ":x"] [:val "y"]]]
          " "
          [:Segment [:tag ":c"]
           ~@(s "foo")]]]
        ~@(s " bar.\n")]])

    (test-parse
     "segments/spanning-double-newline.hd"
     `[:Document
       [:Block
        ~@(s "Test ")
        [:Segment [:tag ":a"]
         [:Attrs [:KVPair [:key ":x"] [:val "y"]]]
         ~@(s " foo\n\n")
         ~@(s "bar baz")]
        ~@(s " quux.\n")]])
    ))
