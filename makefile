cleanwhitespace:
	sources=`find src/butterseal/src/edu/smcm/gamedev/butterseal -name *.java`
#	$(foreach file, $(sources), echo $(file);)
	echo $(sources)
	for number in 1 2 3 4
	do
		echo $(number)
	done
