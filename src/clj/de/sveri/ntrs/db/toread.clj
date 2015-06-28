(ns de.sveri.ntrs.db.toread
  (:require [korma.core :refer [defentity many-to-many select where limit insert values update delete set-fields]])
  (:import (java.util UUID Date)))

(declare TOREAD)

(defentity TOREAD)

(defn get-all-toreads [user]
  (select TOREAD
          (where {:USER user})))

(defn get-toread-by-uuid [uuid] (first (select TOREAD (where {:UUID uuid}) (limit 1))))

(defn create-toread [title link description done tags author user]
  (insert TOREAD (values {:TITLE    title :LINK link :DESCRIPTION description :DONE done :TAGS tags :AUTHOR author
                          :UUID     (str (UUID/randomUUID)) :USER user
                          :ADDED_AT (Date.)})))

(defn update-toread [uuid fields]
  (update TOREAD (set-fields fields) (where {:UUID uuid})))

(defn delete-toread [uuid] (delete TOREAD (where {:UUID uuid})))