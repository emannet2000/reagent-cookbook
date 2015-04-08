# Problem

You want to use an input element with [re-frame](https://github.com/Day8/re-frame) in your [reagent](https://github.com/reagent-project/reagent) web application.

# Solution

[Demo](http://rc-re-frame-input.s3-website-us-east-1.amazonaws.com/)

*Steps*

1. Create a new project
2. Add re-frame to `project.clj` :dependencies vector
3. Add re-frame and reagent's `reaction` macro to `core.cljs` namespace
4. Set up the application's in-memory "database"
5. Register subscription to `:name` in our "database"
6. Create a handler for updating `:name` in our "database"
7. Subscribe to `:name` in the `home` component
8. Create an input element that will dispatch the `:update-name` handler on-change. Also, create a div to display the updated name

#### Step 1: Create a new project

```
$ lein new rc re-frame-input
```

#### Step 2: Add re-frame to `project.clj` :dependencies vector

```clojure
[re-frame "0.2.0"]
```

#### Step 3: Add re-frame and reagent's `reaction` macro to `core.cljs` namespace

Navigate to `src/cljs/re_frame_input/core.cljs`.

```clojure
(ns re-frame-input.core
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]))
```

#### Step 4: Set up the application's in-memory "database"

We are creating a map,`initial-state`, that we use in the `:initialize-db` handler.  We call the `:initialize-db` handler with `re-frame/dispatch-sync`, which creates our applications in-memory "database".

```clojure
(def initial-state
  {:name "FIXME"})

(re-frame/register-handler
 :initialize-db
 (fn [_ _]
   initial-state))

(re-frame/dispatch-sync [:initialize-db])
```

#### Step 5: Register subscription to `:name` in our "database"

```clojure
(re-frame/register-sub
 :name
 (fn [db _]
   (reaction (:name @db))))
```

#### Step 6: Create a handler for updating `:name` in our "database"

```clojure
(re-frame/register-handler
 :update-name
 (fn [db [_ updated-name]]
   (assoc db :name updated-name)))
```

#### Step 7: Subscribe to `:name` in the `home` component

```clojure
(defn home []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div [:h1 "Welcome to Reagent Cookbook!"]
	  
       ])))
```

#### Step 8: Create an input element that will dispatch the `:update-name` handler on-change. Also, create a div to display the updated name

```clojure
(defn home []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div [:h1 "Welcome to Reagent Cookbook!"]
;; ATTNETION \/
       [:input {:type "text"
                :value @name
                :on-change #(re-frame/dispatch [:update-name (-> % .-target .-value)])}]
       [:div "My name is " @name "."]
;; ATTENTION /\
       ])))
```

# Usage

Compile cljs files.

```
$ lein cljsbuild once
```

Start a server.

```
$ lein ring server
```
