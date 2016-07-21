(ns wireworld.css
  (:require [garden.def :refer [defstyles]]))

(def cell-size 15)

(defstyles screen
           [:* {:padding 0
                :margin  0}]
           [:body {:color "#8e44ad"}]
           [:div.line {:height (str (+ cell-size 2) "px")}]
           [:div.cell {:display "inline-block"
                       :width   (str cell-size "px")
                       :height  (str cell-size "px")}
            [:div {:width  "100%"
                   :height "100%"}
             [:&.empty {:background-color "#2c3e50"
                        :border           "1px solid #2c3e50"}]
             [:&.conductor {:background-color "#f1c40f"
                            :border           "1px solid #f39c12"}]
             [:&.head {:background-color "#ecf0f1"
                       :border           "1px solid #bdc3c7"}]
             [:&.tail {:background-color "#3498db"
                       :border           "1px solid #2980b9"}]]]
           [:button {:margin  "5px"
                     :padding "4px 20px"}]
           )

