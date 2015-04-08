(ns re-frame-input.core
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]))

;; Step 4
(def initial-state
  {:name "FIXME"})

(re-frame/register-handler
 :initialize-db
 (fn [_ _]
   initial-state))

(re-frame/dispatch-sync [:initialize-db])

;; Step 5
(re-frame/register-sub
 :name
 (fn [db _]
   (reaction (:name @db))))

;; Step 6
(re-frame/register-handler
 :update-name
 (fn [db [_ updated-name]]
   (assoc db :name updated-name)))

;; Steps 7 and 8
(defn home []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div [:h1 "Welcome to Reagent Cookbook!"]
       [:input {:type "text"
                :value @name
                :on-change #(re-frame/dispatch [:update-name (-> % .-target .-value)])}]
       [:div "My name is " @name "."]
       ])))

(reagent/render-component [home]
                          (.getElementById js/document "app"))
