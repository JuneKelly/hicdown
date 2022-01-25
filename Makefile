##
# Project Title
#
# @file
# @version 0.1

test:
	clojure -T:build test

format:
	clojure -M:format fix

.PHONY: test format
# end
