blog.column = {
  post() {
    return [
      {
        title: '번호',
        name: 'postNo',
        width: 50,
        align: 'center',
        formatter: value => `<a href="/admin/post/${value}">${value}</a>`
      },
      {
        title: '제목',
        name: 'title',
        formatter: (value, row) => `<a href="/admin/post/${row.no}">${value}</a>`
      },
      {
        title: '카테고리',
        name: 'categoryMame',
        width: 120,
        formatter: (value, row) => row.category.name
      },
      {
        title: '태그',
        name: 'tags',
        width: 100,
        formatter: value => value.map(tag => tag.name).join(',')
      },
      {
        title: '상태',
        name: 'publicYn',
        width: 50,
        align: 'center',
        formatter: value => (value ? '공개' : '비공개')
      },
      {
        title: '수정일',
        name: 'updateYmdt',
        width: 150,
        align: 'center',
        formatter: value => blog.common.getDateInKR(value)
      },
      {
        title: '등록일',
        name: 'registerYmdt',
        width: 150,
        align: 'center',
        formatter: value => blog.common.getDateInKR(value)
      }
    ];
  },
  extraData() {
    return [
      {
        title: '키',
        name: 'key',
        width: 80,
        align: 'center',
        editOptions: {
          type: 'text',
          useViewMode: false
        }
      },
      {
        title: '값',
        name: 'value',
        editOptions: {
          type: 'text',
          useViewMode: false
        }
      },
      {
        title: '삭제',
        width: 10,
        align: 'center',
        className: 'removeRow',
        formatter: () => `<i class="fa fa-times action" data-action="remove"/>`,
        editOptions: {
          onFocus: e => console.log(e)
        }
      }
    ];
  }
};

