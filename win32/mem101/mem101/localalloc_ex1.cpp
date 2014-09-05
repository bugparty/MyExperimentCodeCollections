#include "stdafx.h"

void localalloc_moveable(){
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
	LocalUnlock(hMem);
	assert(p != NULL);
	lstrcpy(p, _T("hello,this is the basic usage of LocalAlloc&LocalLock&LocalFree with moveable memory"));
	_tprintf(_T("%s\n"), (LPTSTR)p);


	LocalUnlock(hMem);
	LocalFree(hMem);

}
void localalloc_fixed(){
	LPTSTR hMem = (LPTSTR)LocalAlloc(LPTR, 256);
	assert(hMem != NULL);
	/*LHND Combines LMEM_MOVEABLE and LMEM_ZEROINIT.
	LMEM_FIXED Allocates fixed memory. The return value is a pointer to the memory object.
	LMEM_ZEROINIT Initializes memory contents to zero.
	*/
	lstrcpy(hMem, _T("hello,this is the basic usage of LocalAlloc&LocalLock&LocalFree with fixed memory"));
	_tprintf(_T("%s\n"), (LPTSTR)hMem);

	LocalFree(hMem);
}