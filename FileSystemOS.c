#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <dirent.h>
#include <stdbool.h>
#include <errno.h>
#define MAX 1024
#define MAX_PATH_LEN 256


#pragma pack(push,1)
typedef struct section_header
{
	 char section_name[16];
	  int sect_type;
	  int sect_offset;
	  int sect_size;
}SECTION;
// #pragma pop
char magic;

int write_operation(int fd, SECTION *se) {
    return write(fd, (void*) se, sizeof(SECTION));
}

int read_operation (int fd, SECTION *se){
    return read(fd, (void*) se, sizeof(SECTION));
}

void parse(char *fileName)
{
    unsigned int version = 0;
    unsigned int no_of_sections = 0;
	int fd;
	char buf[MAX];
	int bytesRead;
    unsigned int buf2[MAX];
    long header_size;
	if ((fd = open(fileName, O_RDONLY)) < 0)
	{
		printf("ERROR\nopening the file");
		exit(1);
	}
    lseek(fd,-1,SEEK_END);
	if ((bytesRead = read(fd, buf, 1)) < 0)
	{
		printf("ERROR\nreading from the file!");
        exit(1);
	}
    buf[1]=0;
    magic = buf[0];
    
    if (magic != 'm')
	{
        printf("ERROR\nwrong magic");
		exit(1);
	}
    lseek(fd,-3,SEEK_END);
	if ((bytesRead = read(fd, &header_size, 2)) < 0)
	{
		printf("ERROR\nreading from the file!");
        exit(1);
	}
   
    lseek(fd,-header_size,SEEK_END);
    if ((bytesRead = read(fd, buf2, 1)) < 0)
	{
		printf("ERROR\nreading from the file!");
        exit(1);
	} 
    version = buf2[0];  
    
   	if ((version < 89) || (version > 182))
	{
		printf("ERROR\nwrong version");
		exit(1);
	} 
    if ((bytesRead = read(fd, buf2, 1)) < 0)
	{
		printf("ERROR\nreading from the file!");
        exit(1);
	} 
    no_of_sections = buf2[0];
    if ((no_of_sections < 4) || (no_of_sections > 20))
	{
		printf("ERROR\nwrong sect_nr");
		exit(1);
	}
     for (int i = 0 ; i < no_of_sections; i++) {
        SECTION sect1 ;
        if((read_operation(fd, &sect1)) < 0) {
            printf("ERROR\nreading from file");
            exit(1); }
        if((sect1.sect_type != 10) && (sect1.sect_type != 20) && (sect1.sect_type != 72))
        {
            printf("ERROR\nwrong sect_types");
            exit(1);
        }
     }
    lseek(fd,-(no_of_sections*sizeof(SECTION)),SEEK_CUR);
    printf("SUCCESS\n");
    printf("version=%d\n", version);
   	printf("nr_sections=%d\n", no_of_sections);
    for (int i = 0 ; i < no_of_sections; i++) {
        SECTION sect ;
        if((read_operation(fd, &sect)) < 0) {
            printf("ERROR\nreading from file");
            exit(1); }
       
        
        printf("section%d: ",i+1);
        for(int j = 0; j < 16;j++) {
            if(sect.section_name[j] != '\x00') printf("%c",sect.section_name[j]);
        }
        printf(" %d %d\n",sect.sect_type,sect.sect_size);
    }
	
}

void printAllFiles(char* dirname,bool recursive,int size_smaller, char *perm_string) {
    DIR *dir;
    struct dirent *dirEntry = NULL;
    struct stat inode;
    char name[MAX_PATH_LEN];
    
    bool matchingpermissions = false;
    dir = opendir(dirname);
    if(dir == 0) {
        printf ("Error opening this dir");
    }
    while ((dirEntry=readdir(dir)) != 0) {
        char perm[MAX_PATH_LEN]="";
       int r =	snprintf(name, MAX_PATH_LEN, "%s/%s", dirname, dirEntry->d_name);
       if (r < 0) {
           abort();
       }
		lstat(name, &inode);

		if (strcmp(dirEntry->d_name, ".") == 0 || strcmp(dirEntry->d_name, "..") == 0)
			continue;

       if (recursive && S_ISDIR(inode.st_mode))
		{
			printAllFiles(name, recursive, size_smaller,perm_string); }

        if(inode.st_mode & S_IRUSR) {
            strcat(perm,"r");
        } 
        else {
            strcat(perm,"-");
        }
        if(inode.st_mode & S_IWUSR) {
            strcat(perm,"w");
        } 
        else {
            strcat(perm,"-");
        }
         if(inode.st_mode & S_IXUSR) {
            strcat(perm,"x");
        } 
        else {
            strcat(perm,"-");
        }
        if(inode.st_mode & S_IRGRP) {
            strcat(perm,"r");
        } 
        else {
            strcat(perm,"-");
        }
         if(inode.st_mode & S_IWGRP) {
            strcat(perm,"w");
        } 
        else {
            strcat(perm,"-");
        }
         if(inode.st_mode & S_IXGRP) {
            strcat(perm,"x");
        } 
        else {
            strcat(perm,"-");
        }
        if(inode.st_mode & S_IROTH) {
            strcat(perm,"r");
        } 
        else {
            strcat(perm,"-");
        }
         if(inode.st_mode & S_IWOTH) {
            strcat(perm,"w");
        } 
        else {
            strcat(perm,"-");
        }
         if(inode.st_mode & S_IXOTH) {
            strcat(perm,"x");
        } 
        else {
            strcat(perm,"-");
        }
       if(perm_string != NULL) 
       {
           if(strcmp(perm_string,perm) == 0) {
               matchingpermissions = true;
           }
       } else {
           matchingpermissions = true;
       }
       
        if (size_smaller == 0 ) {
            if(matchingpermissions)
                printf("%s\n", name);
        } else {
                if (S_ISREG(inode.st_mode) && inode.st_size < size_smaller)
			            {
                              if(matchingpermissions)
				              printf("%s\n", name);
                              		    }
        }
    }
    closedir(dir);
}
void findAllFiles(char* dirname) {
    DIR *dir ;
    struct dirent *dirEntry = NULL;
    struct stat inode;
    char name[MAX_PATH_LEN];
    
    int nrOfSections=0;
    dir = opendir(dirname);
    if(dir == 0) {
        printf ("ERROR\ninvalid directory path");
        exit(1);
    }
    while ((dirEntry=readdir(dir)) != 0) {
       
       int r =	snprintf(name, MAX_PATH_LEN, "%s/%s", dirname, dirEntry->d_name);
       if (r < 0) {
           abort();
       }
		lstat(name, &inode);
         
		if (strcmp(dirEntry->d_name, ".") == 0 || strcmp(dirEntry->d_name, "..") == 0)
			continue;

       if (S_ISDIR(inode.st_mode))
		{
			findAllFiles(name); }
        else {
            if(S_ISREG(inode.st_mode)) {
                
                     unsigned int version = 0;
    unsigned int no_of_sections = 0;
	int fd;
	char buf[MAX];
  
	int bytesRead;
   
    unsigned int buf2[MAX];
    long header_size;
	if ((fd = open(name, O_RDONLY)) < 0)
	{
		printf("ERROR\nopening the file");
		exit(1);
	}
    lseek(fd,-1,SEEK_END);
	if ((bytesRead = read(fd, buf, 1)) < 0)
	{
		printf("ERROR\nreading from the file!");
        exit(1);
	}
    buf[1]=0;
    magic = buf[0];
    
    if (magic != 'm')
	{
       printf("Could not read section magic");
		exit(1);
	}
    lseek(fd,-3,SEEK_END);
	if ((bytesRead = read(fd, &header_size, 2)) < 0)
	{
		printf("ERROR\nreading from the file!");
        exit(1);
	}
   
    lseek(fd,-header_size,SEEK_END);
    if ((bytesRead = read(fd, buf2, 1)) < 0)
	{
		printf("ERROR\nreading from the file!");
        exit(1);
	} 
    version = buf2[0];  
    
   	if ((version < 89) || (version > 182))
	{
		printf("Could not read section version");
		exit(1);
	} 
    if ((bytesRead = read(fd, buf2, 1)) < 0)
	{
		printf("ERROR\nreading from the file!");
        exit(1);
	} 
    no_of_sections = buf2[0];
    if ((no_of_sections < 4) || (no_of_sections > 20))
	{
		printf("Could not read section no");
		exit(1);
	}
    

    for (int i = 0 ; i < no_of_sections; i++) {
        SECTION sect ;
        if((read_operation(fd, &sect)) < 0) {
            printf("Could not read section");
                        exit(1);

        }
        
        if((sect.sect_type != 10) && (sect.sect_type != 20) && (sect.sect_type != 72))
        {
            printf("Could not read section type");
            exit(1);
        }
        
    }
    lseek(fd,-(no_of_sections*sizeof(SECTION)),SEEK_CUR);
	
    for (int i = 0 ; i < no_of_sections; i++) {
        SECTION sect ;
        
        int nrofLines = 0;
        
        if((read_operation(fd, &sect)) < 0) {
            printf("Could not read section in here");
            exit(1);
        }
        lseek(fd,-sect.sect_size,SEEK_CUR);
        for(int j = 0; j < sect.sect_size; j++) {
           if((bytesRead=read(fd,buf,1))>0) {
             if(buf[0]=='\r') {     
                    // if(buf[1]=='\n')
                      nrofLines++;  
                }     
            } 
         }
        
         if(nrofLines == 13) 
             nrOfSections++;
       }                
    }       printf("%d -",nrOfSections);
            if(nrOfSections >= 3) {
                 printf("SUCCESS\n%s\n",name);
            } 
        }
    }
 }
      
    


int main(int argc, char **argv){
      bool recursive;
      char *path;
      bool parseIt;
      char *max_size;
      int max_s = 0;
      char *perm_string;
    if( argc < 2 ) {
        printf("ERROR!Not enough arguments !");
        return -1;
        } 
    if( strcmp(argv[1],"variant") == 0 ) {
            printf("\n22964\n");
        }
    if( strcmp(argv[1], "list") == 0 ) {
            if(argc < 3 ) {
                printf("ERROR!Not enough arguments !");
                return -1;
            }
            for (int i = 0; i < argc ; i++) {
                
                if (strstr(argv[i],"path=") != NULL) {
                    path = strstr(argv[i],"path=");
                    path+=5;
                    
                }
                if(strcmp("recursive",argv[i])==0) {
                    recursive = true;
                }

                if (strstr(argv[i],"size_smaller=") != NULL) {
                    max_size = strstr(argv[i],"size_smaller=");
                    max_size+=13;
                    max_s=atoi(max_size);
                }
                if (strstr(argv[i],"permissions=") != NULL) {
                    perm_string = strstr(argv[i],"permissions=");
                    perm_string+=12;
                }
                
            }
                 struct stat fileMetadata;
                 if( stat(path,&fileMetadata) < 0 ) {
                       printf("%d is errno %s ",errno,path);
                        return -1;
                    }
                    if(S_ISDIR(fileMetadata.st_mode)) {   
                        printf("\nSUCCESS\n");     
                        printAllFiles(path,recursive,max_s,perm_string);
                    } else {
                        printf("\nERROR\n");
                        printf("invalid directory path");
                    }
                    
                }  else {
                    if(strcmp("findall",argv[1])==0) {
                        for(int  i = 0; i < argc; i++) {
                      if (strstr(argv[i],"path=") != NULL) {
                          path = strstr(argv[i],"path=");
                          path+=5;
                    
                       }
                    }
                    findAllFiles(path);
                    }
                }
                for (int i = 0; i < argc ; i++) {             
                 if(strcmp("parse",argv[i])==0) {
                        parseIt = true;
                 }

                }
                if(parseIt) {
                    for(int  i = 0; i < argc; i++) {
                      if (strstr(argv[i],"path=") != NULL) {
                          path = strstr(argv[i],"path=");
                          path+=5;
                    
                       }
                    }
                    parse(path);
                }
             
      return 0;
}
    
