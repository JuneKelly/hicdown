(ns wtf.halt.hicdown.html
  (:require [wtf.halt.hicdown.html-util :as util]))

(defn tree-to-html [tree]
  (util/render-node tree))
