##git clone https://github.com/VundleVim/Vundle.vim.git ~/.vim/bundle/Vundle.vim
# 安装vim插件
vim -c PluginInstall -c q -c q

# 安装插件运行需要依赖的一些组件
#cd /root/.vim/bundle/YouCompleteMe/ && python3 install.py --clang-complete &&  python3 install.py --clangd-completer
cd /root/.vim/bundle/YouCompleteMe/ && python3 install.py --clang-complete
