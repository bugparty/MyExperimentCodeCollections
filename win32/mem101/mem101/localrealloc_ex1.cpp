#include "stdafx.h"


void localrealloc_fixed(){
	LPTSTR hMem = (LPTSTR)LocalAlloc(LPTR, 512);
	assert(hMem != NULL);
	/*LHND Combines LMEM_MOVEABLE and LMEM_ZEROINIT.
	LMEM_FIXED Allocates fixed memory. The return value is a pointer to the memory object.
	LMEM_ZEROINIT Initializes memory contents to zero.
	*/
	lstrcpy(hMem, _T("hello,this is the basic usage of LocalAlloc&LocalLock&LocalFree with fixed memory"));
	_tprintf(_T("%s\n"), (LPTSTR)hMem);
	LPTSTR hNew = (LPTSTR)LocalReAlloc(hMem, 2048, LPTR);
	//MEM_FIXED means you can not realloc a larger memory then before,but only smaller
	//so this is going to be failed
	if (hNew == NULL){
		HLOCAL hlocal = NULL; //Buffer that gets the error message string
		DWORD dwError = GetLastError();
		//Get the error code`s textual description
		BOOL fOk = FormatMessage(
			FORMAT_MESSAGE_FROM_SYSTEM | FORMAT_MESSAGE_ALLOCATE_BUFFER,
			NULL, dwError, MAKELANGID(LANG_ENGLISH, SUBLANG_ENGLISH_US),
			(LPTSTR)&hlocal, 0, NULL);
		if (hlocal != NULL){
			wprintf(_T("Error When realloc memory: %s"), (LPTSTR)hlocal);
			LocalFree(hlocal);
		}
	
	}
	//this will be success
	 hNew = (LPTSTR)LocalReAlloc(hMem, 376, LPTR);
	if (hNew == NULL){
		HLOCAL hlocal = NULL; //Buffer that gets the error message string
		DWORD dwError = GetLastError();
		//Get the error code`s textual description
		BOOL fOk = FormatMessage(
			FORMAT_MESSAGE_FROM_SYSTEM | FORMAT_MESSAGE_ALLOCATE_BUFFER,
			NULL, dwError, MAKELANGID(LANG_ENGLISH, SUBLANG_ENGLISH_US),
			(LPTSTR)&hlocal, 0, NULL);
		if (hlocal != NULL){
			wprintf(_T("Error When realloc memory: %s"), (LPTSTR)hlocal);
			LocalFree(hlocal);
		}

	}
	lstrcpy(hNew, _T("hello,this is the basic usage of LocalAlloc&LocalLock&LocalFree&LocalRealloc with fixed memory AAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));

	_tprintf(_T("%s\n"), (LPTSTR)hMem);
	_tprintf(_T("%s\n"), (LPTSTR)hNew);
	LocalFree(hMem);
}

void localrealloc_moveable(){
	HLOCAL hMem = LocalAlloc(LHND, 256);
	assert(hMem != NULL);
	/*LHND Combines LMEM_MOVEABLE and LMEM_ZEROINIT.
	LMEM_MOVEABLE
	Allocates movable memory. Memory blocks are never moved in physical memory, but they can be moved within the default heap.
	The return value is a handle to the memory object. To translate the handle to a pointer, use the LocalLock function.
	This value cannot be combined with LMEM_FIXED.
	LMEM_ZEROINIT Initializes memory contents to zero.
	*/
	LPTSTR p = (LPTSTR)LocalLock(hMem);
	assert(p != NULL);
	lstrcpy(p, _T("hello,this is the basic usage of LocalAlloc&LocalLock&LocalFree with moveable memory"));
	_tprintf(_T("%s\n"), (LPTSTR)p);
	LocalUnlock(hMem);
	HLOCAL hNew = LocalReAlloc(hMem, 1024, LHND);
	if (hNew == NULL){
		HLOCAL hlocal = NULL; //Buffer that gets the error message string
		DWORD dwError = GetLastError();
		//Get the error code`s textual description
		BOOL fOk = FormatMessage(
			FORMAT_MESSAGE_FROM_SYSTEM | FORMAT_MESSAGE_ALLOCATE_BUFFER,
			NULL, dwError, MAKELANGID(LANG_ENGLISH, SUBLANG_ENGLISH_US),
			(LPTSTR)&hlocal, 0, NULL);
		if (hlocal != NULL){
			wprintf(_T("Error When realloc memory: %s"), (LPTSTR)hlocal);
			LocalFree(hlocal);
		}

	}

	LPTSTR p2 = (LPTSTR)LocalLock(hNew);
	lstrcpy(p2, _T("hello,this is the basic usage of LocalAlloc&LocalLock&LocalFree with moveable memory!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"));
	_tprintf(_T("%s\n"), (LPTSTR)p2);
	LocalUnlock(hNew);

	
	LocalFree(hNew);

}