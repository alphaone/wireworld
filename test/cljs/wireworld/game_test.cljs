(ns wireworld.game-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [wireworld.game :as game]))

;; :e empty
;; :c conductor
;; :h head
;; :t tail

(def empty-board [[:e :e :e]
                  [:e :e :e]
                  [:e :e :e]])

(deftest advance-test
  (testing "it advances the board by a single step"
    (is (= empty-board
           (game/advance-board empty-board)))
    (is (= [[:e :e :e :e :e]
            [:e :c :t :h :e]
            [:e :e :e :e :e]]
           (game/advance-board [[:e :e :e :e :e]
                                [:e :t :h :c :e]
                                [:e :e :e :e :e]])))
    (is (= [[:e :e :e :e :e]
            [:e :c :t :e :e]
            [:e :e :e :h :e]
            [:e :c :t :e :e]
            [:e :e :e :e :e]]
           (game/advance-board [[:e :e :e :e :e]
                                [:e :t :h :e :e]
                                [:e :e :e :c :e]
                                [:e :t :h :e :e]
                                [:e :e :e :e :e]])))
    
    (is (= [[:e :e :e :e :e :e :e]
            [:e :c :t :e :e :e :e]
            [:e :e :e :c :t :c :e]
            [:e :c :t :e :e :e :e]
            [:e :e :e :e :e :e :e]]
           (game/advance-board [[:e :e :e :e :e :e :e]
                                [:e :t :h :e :e :e :e]
                                [:e :e :e :c :h :t :e]
                                [:e :t :h :e :e :e :e]
                                [:e :e :e :e :e :e :e]])))))

(deftest neighbors-test
  (testing "it finds neighbors of a cell"
    (let [board [[:e :e :e :e :e]
                 [:e :c :c :c :e]
                 [:e :c :e :c :e]
                 [:e :h :t :c :e]
                 [:e :e :e :e :e]]]
      (is (= [:c :c :c :c :c :h :t :c]
             (game/neighbors board [2 2])))
      (is (= [:e :e :c]
             (game/neighbors board [0 0])))
      (is (= [:c :e :e]
             (game/neighbors board [4 4])))
      (is (= [:h :t :c :e :e]
             (game/neighbors board [2 4]))))))

(deftest next-state-test
  (testing "it determines the next state of a cell"
    (testing "empty -> empty"
      (is (= :e
             (game/next-state [] :e))))
    (testing "head -> tail"
      (is (= :t
             (game/next-state [] :h))))
    (testing "tail -> conductor"
      (is (= :c
             (game/next-state [] :t))))
    (testing "conductor -> head if exactly one or two of the neighbouring cells are heads"
      (is (= :c
             (game/next-state [:c :e :e :e] :c)))
      (is (= :h
             (game/next-state [:h :e :e :e] :c)))
      (is (= :h
             (game/next-state [:h :h :e :e] :c)))
      (is (= :c
             (game/next-state [:h :h :h :e] :c))))
    ))