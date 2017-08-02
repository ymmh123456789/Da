# ./hw1.sh sourceDir destinationDir

if [ "$2" == " " ] || [ ! -d $2 ] ; then
	mkdir $2
fi
filename=`ls $1`
newFilename=0 
for i in $filename
do
	newFilename=$(($newFilename+1))
	cat $1/$i | grep "oid" > "$2/log$newFilename"
done

echo "SUCCESSED!"