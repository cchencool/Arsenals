set nocompatible              " be iMproved, required
filetype off                  " required

" set the runtime path to include Vundle and initialize
set rtp+=~/.vim/bundle/Vundle.vim
call vundle#begin()
" alternatively, pass a path where Vundle should install plugins
"call vundle#begin('~/some/path/here')

" let Vundle manage Vundle, required
Plugin 'VundleVim/Vundle.vim'

Plugin 'Valloric/YouCompleteMe'
" The following are examples of different formats supported.
" Keep Plugin commands between vundle#begin/end.
" plugin on GitHub repo
"Plugin 'tpope/vim-fugitive'

" plugin from http://vim-scripts.org/vim/scripts.html
"Plugin 'L9'

" Git plugin not hosted on GitHub
"Plugin 'git://git.wincent.com/command-t.git'
" git repos on your local machine (i.e. when working on your own plugin)
"Plugin 'file:///home/gmarik/path/to/plugin'

" The sparkup vim script is in a subdirectory of this repo called vim.
" Pass the path to set the runtimepath properly.
"Plugin 'rstacruz/sparkup', {'rtp': 'vim/'}

" Install L9 and avoid a Naming conflict if you've already installed a
" different version somewhere else.
"Plugin 'ascenator/L9', {'name': 'newL9'}

" All of your Plugins must be added before the following line
call vundle#end()            " required
filetype plugin indent on    " required
" To ignore plugin indent changes, instead use:
"filetype plugin on
"
" Brief help
" :PluginList       - lists configured plugins
" :PluginInstall    - installs plugins; append `!` to update or just :PluginUpdate
" :PluginSearch foo - searches for foo; append `!` to refresh local cache
" :PluginClean      - confirms removal of unused plugins; append `!` to auto-approve removal
"
" see :h vundle for more details or wiki for FAQ
" Put your non-Plugin stuff after this line

syntax on
set encoding=utf-8
set fileencodings=utf-8,gb18030,ucs-bom,cp936
set fileencoding=utf-8
set helplang=cn
set nu!
set autoindent
set shiftwidth=4
set tabstop=4
set hlsearch
set autoindent
set ruler
set bg=light
set ignorecase
set clipboard=unnamed
set guifont=Monaco:h12
set numberwidth=2
set backspace=2 "make backspace work like most other apps
"set bg=dark


let mapleader = ","
"key maps
nnoremap <leader>ev :vsplit $MYVIMRC<cr>
nnoremap <leader>sv :source $MYVIMRC<cr>
nnoremap <leader>" viw<esc>a"<esc>hbi"<esc>lel 
"nnoremap <leader>xml :call FormatXml()<cr>

nnoremap - dd
nnoremap <space> viw

nnoremap ∆ :m .+1<CR>==
nnoremap ˚ :m .-2<CR>==
inoremap ∆ <esc>:m .+1<CR>==gi
inoremap ˚ <esc>:m .-2<CR>==gi
vnoremap ∆ :m '>+1<CR>==gv
vnoremap ˚ :m '<-2<CR>==gv

inoremap <c-d> <esc>dd<up>$
inoremap <c-U> <esc>h<space>Uea
"inoremap <c-u> <esc><space>u$a

inoremap ( ()<Esc>i
inoremap [ []<Esc>i
"inoremap { {<CR>}<Esc>O
"autocmd Syntax html,vim
inoremap < <lt>><Esc>i| inoremap > <c-r>=ClosePair('>')<CR>
inoremap ) <c-r>=ClosePair(')')<CR>
inoremap ] <c-r>=ClosePair(']')<CR>
"inoremap } <c-r>=CloseBracket()<CR>
inoremap " <c-r>=QuoteDelim('"')<CR>
inoremap ' <c-r>=QuoteDelim("'")<CR>

function ClosePair(char)
	if getline('.')[col('.') - 1] == a:char
		return "\<Right>"
	else
		return a:char
	endif
endf

function CloseBracket()
	if match(getline(line('.') + 1), '\s*}') < 0
		return "\<CR>}"
	else
		return "\<Esc>j0f}a"
	endif
endf

function QuoteDelim(char)
	let line = getline('.')
	let col = col('.')
	if line[col - 2] == "\\"
		return a:char
	elseif line[col - 1] == a:char
		return "\<Right>"
	else
		return a:char.a:char."\<Esc>i"
	endif
endf

function! ToggleSyntax()
   if exists("g:syntax_on")
      syntax off
   else
      syntax enable
   endif
endfunction

function! SortAndRemoveDuplicate()
sort
g/^\(.*\)$\n\1$/d
endfunction

function! ChangeKPIFormularToV2R1()
%s /[\[].[^\[]*[\]\]]\.//g
%s /\[/[Counter].[NE Interface].[mib-2-Interface].[/g
%s /300/[Counter].[Interface].[Interface_timeSet].[Interface_Delta_ts]/g
endfunction

function! ChangeKPIFormularToV2R1AndDelDuplicate()
%s /[\[].[^\[]*[\]\]]\.//g
%s /\[/[Counter].[NE Interface].[mib-2-Interface].[/g
%s /300/[Counter].[Interface].[Interface_timeSet].[Interface_Delta_ts]/g
sort
g/^\(.*\)$\n\1$/d
endfunction

function! ChangeKPIFormularToV2R1()
%s /[\[].[^\[]*[\]\]]\.//g
%s /\[/[Counter].[NE Interface].[mib-2-Interface].[/g
%s /300/[Counter].[Interface].[Interface_timeSet].[Interface_Delta_ts]/g
endfunction

function! ChangeKPIFormularToV2R1AndDelDuplicateWithSpecial()
%s /[\[].[^\[]*[\]\]]\.//g
%s /\[/[Counter].[NE Interface].[mib-2-Interface].[/g
%s /300/[Counter].[Interface].[Interface_timeSet].[Interface_Delta_ts]/g
sort
g/^\(.*\)$\n\1$/d
endfunction

"Get Simplest KPI formula
function! GetSimplestKPIFormula()
%s /[\[].[^\[]*[\]\]]\.//g
endfunction

"Get CounterNames from KPI
function! GetCounterNameListfromFormulas()
%s /\].[^\[]*\[/\r/g  
%s /\].[^\[]*$/\r/g 
%s /.[^\]]*\[/\r/g 
%s /\[/\r/g
%s /\]/\r/g
%s /^I$//g
call SortAndRemoveDuplicate()
endfunction

"Get Counter ID List from Telco Formulas
function! GetTelcoCounterIDListfromFormulas()
%s /}.[^\{]*{/\r/g
%s /}.[^\{]*$/\r/g
%s /.[^}]*{/\r/g
%s /{/\r/g
%s /}/\r/g
call SortAndRemoveDuplicate()
%s /^$\n//g
endfunction

"Get Delta Counter ID from Telco Formula
function! GetDeltaCounterIDsFromFormulas()
%s /{\d*},delta,\?/------------------------&\r/g
%s /-*{\d*},delta,\?/\r&/g
%s /^.[^-]\+.*//g
%s /-//g
%s /,delta,\?//g
%s /{//g
%s /}//g
%s /\n$//g
call SortAndRemoveDuplicate()
%s /^$\n//g
endfunction

"Get Delta Counter Name from Telco Formula
function! GetDeltaCounterNamesFromFormulas()
%s /\[.*\],delta,\?/------------------------&\r/g
%s /-*\[.*\],delta,\?/\r&/g
%s /^.[^-]\+.*//g
%s /-//g
%s /,delta,\?//g
%s /[//g
%s /]//g
%s /\n$//g
call SortAndRemoveDuplicate()
endfunction

"Quote List Words for 'in sql'
function! QuoteLinesForSql()
%s /^/'/g
%s /$/',/g
1s /^'/('/g
$s /',$/')/g
endfunction

"格式化xml
function FormatXml()
	set filetype=xml
	:%s /> \+</></g
	:%s /></>\r</g "把><替换成>回车<
	:%s /\\"/"/g
	:normal gg=G<cr>
endfunction
nnoremap <leader>xml :call FormatXml()<cr>

function FormatXmlToOneLineNoTrans()
	set filetype=xml
	":%s /\n//g
	:%s />[ \f\r\t\n]\+</></g
	"":%s /"/\\"/g
endfunction

function FormatXmlToOneLine()
	set filetype=xml
	":%s /\n//g
	:%s />[ \f\r\t\n]\+</></g
	:%s /"/\\"/g
endfunction

"格式化Json
function FormatJson()
	set filetype=json
	:%!python -m json.tool
endfunction

"格式化SQL
function FormatSql()
	set filetype=sql
	:%SQLUFormatter
endfunction
