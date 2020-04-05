echo Running EKGS init scripts...
for file in `ls /ekgs/init` 
do 
  script="/ekgs/init/$file"
  echo $script
  sh "$script" &
  mv "$script" /ekgs/init_history/
done