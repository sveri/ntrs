(ns de.sveri.ntrs.routes.toread
  (:require [compojure.core :refer [routes GET POST DELETE]]
            [noir.response :as resp]
            [ring.util.response :refer [response content-type]]
            [taoensso.timbre :as timb]
            [de.sveri.ntrs.db.toread :as db]
            [de.sveri.ntrs.layout :as layout]
            [de.sveri.ntrs.service.user :as serv]))

(defn convert-boolean [b] (if (= "on" b) true false))

(defn index-page []
  (layout/render "/toread/index.html" {:toreads (db/get-all-toreads (serv/get-logged-in-username))
                                       :cols ["title" "link" "description" "done" "tags" "author" ]}))

(defn create-page []
  (layout/render "/toread/create.html" {:create_update "Create"}))

(defn update-page [uuid]
  (let [toread (db/get-toread-by-uuid uuid)]
    (layout/render "/toread/create.html" {:toread toread :create_update "Update"})))

(defn delete-page [uuid]
  (layout/render "/toread/delete.html" {:uuid uuid}))

(defn create [title link description done tags author ]
  (try
    (db/create-toread title link description (convert-boolean done) tags author (serv/get-logged-in-username))
    (catch Exception e (timb/error e "Something went wrong creating toread.")
                       (layout/flash-result (str "An error occured.") "alert-danger")))
  (resp/redirect "/toread"))

(defn update [uuid title link description done tags author ]
  (try
    (db/update-toread uuid {:TITLE title :LINK link :DESCRIPTION description :DONE (convert-boolean done) :TAGS tags :AUTHOR author  })
    (catch Exception e (timb/error e (str "Something went wrong updating: " uuid))
                       (layout/flash-result (str "An error occured.") "alert-danger")))
  (resp/redirect "/toread"))

(defn delete [uuid delete_cancel]
  (when (= "Delete" delete_cancel)
    (try
     (db/delete-toread uuid)
     (catch Exception e (timb/error e (str "Something went wrong deleting: " uuid))
                        (layout/flash-result (str "An error occured.") "alert-danger"))))
  (resp/redirect "/toread"))

(defn toread-routes []
  (routes
    (GET "/toread" [] (index-page))
    (GET "/toread/create" [] (create-page))
    (GET "/toread/:uuid" [uuid] (update-page uuid))
    (POST "/toread/create" [title link description done tags author ] (create title link description done tags author ))
    (GET "/toread/delete/:uuid" [uuid] (delete-page uuid))
    (POST "/toread/delete" [uuid delete_cancel] (delete uuid delete_cancel))
    (POST "/toread/update" [uuid title link description done tags author ] (update uuid title link description done tags author ))))
