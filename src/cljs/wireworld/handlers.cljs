(ns wireworld.handlers
  (:require [re-frame.core :as re-frame]
            [wireworld.db :as db]
            [wireworld.game :as game]))

(re-frame/register-handler
  :initialize-db
  (fn [_ _]
    db/default-db))

(re-frame/register-handler
  :inc-counter
  (re-frame/path :count)
  (fn [old-count _]
    (inc old-count)))

(defn reset [current]
  (case current
    :e :e
    :c))

(re-frame/register-handler
  :reset
  (re-frame/path :board)
  (fn [old-board _]
    (vec (map (fn [l] (vec (map reset l))) old-board))))

(re-frame/register-handler
  :clear
  (re-frame/path :board)
  (fn [old-board _]
    (vec (map (fn [l] (vec (map (constantly :e) l))) old-board))))

(defn toggle [current]
  (case current
    :e :c
    :c :h
    :h :t
    :t :e))

(re-frame/register-handler
  :toggle
  [(re-frame/path :board) re-frame/trim-v]
  (fn [board [x y]]
    (update-in board [y x] toggle)))

(re-frame/register-handler
  :empty
  [(re-frame/path :board) re-frame/trim-v]
  (fn [board [x y]]
    (assoc-in board [y x] :e)))

(re-frame/register-handler
  :step
  (re-frame/path :board)
  (fn [board _]
    (re-frame/dispatch [:inc-counter])
    (game/advance-board board)))

(re-frame/register-handler
  :play
  (re-frame/path :to)
  (fn [old-to _]
    (js/clearInterval old-to)
    (js/setInterval #(re-frame/dispatch [:step]) 250)))

(re-frame/register-handler
  :stop
  (re-frame/path :to)
  (fn [old-to _]
    (js/clearInterval old-to)))