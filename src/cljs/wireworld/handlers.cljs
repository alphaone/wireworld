(ns wireworld.handlers
  (:require [re-frame.core :as re-frame]
            [wireworld.db :as db]
            [wireworld.game :as game]))

(re-frame/register-handler
  :initialize-db
  (fn [_ _]
    (merge db/default-db (db/ls->board))))

(re-frame/register-handler
  :inc-counter
  (re-frame/path :count)
  (fn [old-count _]
    (inc old-count)))

;; middleware for any handler that manipulates todos
(def board-middleware [(re-frame/path :board)               ;; 1st param to handler will be the value from this path
                       (re-frame/after db/board->ls!)       ;; write to localstore each time
                       re-frame/trim-v])                    ;; remove the first (event id) element from the event vec


(defn reset [current]
  (case current
    :e :e
    :c))

(re-frame/register-handler
  :reset
  board-middleware
  (fn [old-board _]
    (vec (map (fn [l] (vec (map reset l))) old-board))))

(re-frame/register-handler
  :clear
  board-middleware
  (fn [old-board _]
    (vec (map (fn [l] (vec (map (constantly :e) l))) old-board))))

(defn toggle [current]
  (case current
    :e :c
    :c :h
    :h :t
    :t :c))

(re-frame/register-handler
  :toggle
  board-middleware
  (fn [board [x y]]
    (update-in board [y x] toggle)))

(re-frame/register-handler
  :empty
  board-middleware
  (fn [board [x y]]
    (assoc-in board [y x] :e)))

(re-frame/register-handler
  :step
  board-middleware
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