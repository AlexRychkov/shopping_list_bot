#!/usr/bin/env bash

psql -d shopping_list -f drop.sql
psql -d shopping_list -f init.sql