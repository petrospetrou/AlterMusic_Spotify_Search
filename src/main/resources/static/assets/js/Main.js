class SearchComponent extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            showResults: false,
            showSelectedItem:false,
            selectedItem: [],
            searchItems: [],
        }

        this.search = this.search.bind(this);
        this.getInfo = this.getInfo.bind(this);
        this.SearchItemComponent = this.SearchItemComponent.bind(this);
        this.renderSwitchType = this.renderSwitchType.bind(this);
        this.handleSearchKeyDown = this.handleSearchKeyDown.bind(this);
    }

    //Fetching the search data from the "/search" endpoint
    search() {
        var searchInput = document.getElementById('searchInput').value;
        fetch("/search?" + new URLSearchParams({
            searchInput: searchInput
        }))
            .then(response => response.json())
            .then(
                (response) => {
                    console.log(response);

                    this.setState({
                        searchItems: response,
                        showResults: true,
                        showSelectedItem: false
                    });
                },
                (error) => {
                    alert(error);
                }
            )
    }

    //Rendering the searched items
    SearchItemComponent(props) {

        if (props.searchType === 'Track' || props.searchType === 'Album'){
            return (
                <tr onClick={() => this.getInfo(props)} className="search-item">
                    <td><img src={props.imageUrl} style={{width: '50px', height: '50px'}}/></td>
                    <td style={{fontSize: '18px'}}> {props.searchName} </td>
                    <td style={{fontSize: '18px'}}> {props.artistName} </td>
                    <td style={{fontSize: '18px'}}> {props.searchType} </td>
                </tr>
            );
        }else {
            return (
                <tr onClick={() => this.getInfo(props)} className="search-item">
                    <td><img src={props.imageUrl} style={{width: '50px', height: '50px'}}/></td>
                    <td style={{fontSize: '18px'}}> - </td>
                    <td style={{fontSize: '18px'}}> {props.artistName} </td>
                    <td style={{fontSize: '18px'}}> {props.searchType} </td>
                </tr>
            );
        }


    }

    //Needed to remove the search items and show the data
    getInfo(props) {
        this.setState({
            selectedItem: props,
            showSelectedItem: true,
            showResults: false
        });

        document.getElementById('searchInput').value = '';
    }

    //Handles which component should be executed based on the Type
    renderSwitchType(){
        if (this.state.showSelectedItem){
            switch (this.state.selectedItem.searchType)
            {
                case 'Track':
                    return(<TrackInfo id={ this.state.selectedItem.searchId }/>);
                case 'Album':
                    return(<AlbumInfo id={ this.state.selectedItem.searchId }/>);
                case 'Artist':
                    return(<ArtistInfo id={ this.state.selectedItem.searchId }/>);
                default:
                    return(<div>Empty</div>);
            }
        }
        else
        {
            return(null);
        }
    }

    //Used to enable the input to send data when the enter button is pressed
    handleSearchKeyDown(event){
        if (event.key === 'Enter') {
            // Get input value
            this.search();
        }
    }

    render() {
            return (
                <div id="main">
                    <div className="input-group">
                        <div className="input-group">
                            <input className="form-control" type="text" id="searchInput" placeholder="Song, Album, Artist..." onKeyDown={this.handleSearchKeyDown} />
                            <button className="btn btn-primary btn-explore" type="button" onClick={this.search}>Search</button>
                        </div>
                    </div>
                    <section style={ this.state.showResults ? { display: 'flex',marginTop: '18px' } : { display: 'none' }} >
                        <div className="container">
                            <div className="row search-Area" style={{justifyContent: 'center'}}>
                                <div className="col" style={{width: 'auto'}}>
                                    <div className="card" style={{width: '723px', marginLeft: 'auto', marginRight: 'auto', height: 'auto'}}>
                                        <div className="card-body">
                                            <div className="table-responsive" style={{textAlign: 'center', width: '687px'}}>
                                                <table className="table">
                                                    <thead>
                                                    <tr>
                                                        <th style={{width: '50px'}}></th>
                                                        <th style={{width: '120px', fontSize: '20px'}}>Name</th>
                                                        <th style={{width: '120px', fontSize: '20px'}}>Artist</th>
                                                        <th style={{width: '120px', fontSize: '20px'}}>Type</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                        {
                                                            this.state.searchItems.map((item) => {
                                                                return( <this.SearchItemComponent
                                                                    searchName = {item.searchName}
                                                                    artistName = {item.artistName}
                                                                    searchType = {item.searchType}
                                                                    imageUrl = {item.imageUrl}
                                                                    searchId = {item.searchId}/>);
                                                            })
                                                        }
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                    <div className="results" style={ this.state.showSelectedItem ? { display: 'flex',marginTop: '18px', justifyContent: 'center' } : { display: 'none' }} >
                        { this.renderSwitchType() }
                    </div>
                </div>
            );
    }
}

//Component for rendering the track's data
class TrackInfo extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isReady: false,
            id: props.id
        }

        this.fetchTrackInfo = this.fetchTrackInfo.bind(this);

        this.fetchTrackInfo();
    }

    //Fetching the track's data from the "/track" endpoint
    fetchTrackInfo(){
        fetch("/track?" + new URLSearchParams({
            trackId: this.state.id
        }))
            .then(response => response.json())
            .then(
                (response) => {
                    console.log(response);

                    this.setState({
                        itemInfo: response,
                        isReady: true,
                    });
                },
                (error) => {
                    alert(error);
                }
            )
    }

    render() {
        if (this.state.isReady){
            return (
                <div className="results">
                    <div className="container">
                        <div className="row" style={{marginTop: '23px', marginBottom: '23px'}}>
                            <div className="col">
                                <div className="card" style={{position: 'relative'}}>
                                    <div className="card-body" style={{textAlign: 'center', display: 'inline-block'}}>
                                        <img src={ this.state.itemInfo.trackImage} style={{width: '450px', borderRadius: '10px'}}/>
                                    </div>
                                </div>
                            </div>
                            <div className="col">
                                <div className="card" style={{height: '484.891px', overflowY: 'scroll'}}>
                                    <div className="card-body" style={{marginLeft: '15px'}}>
                                        <h4 className="card-title" style={{fontSize: '67.376px', marginBottom: '10px'}}>
                                            <a href={this.state.itemInfo.externalLink} target={'_blank'} style={{textDecoration: 'none', color: 'black'}}>
                                                {this.state.itemInfo.name}
                                            </a>
                                        </h4>
                                        <h4 style={{textDecoration: 'underline', marginBottom: '23px'}}>Overview:</h4>
                                        <h4 style={{fontSize: '30px'}}>Artist: {this.state.itemInfo.artistName} &nbsp;</h4>
                                        <h4 style={{fontSize: '30px'}}>Type: {this.state.itemInfo.type}</h4>
                                        <h4 style={{fontSize: '30px'}}>Album: {this.state.itemInfo.albumName}</h4>
                                        <h4 style={{fontSize: '30px'}}>Disc Number: {this.state.itemInfo.discNumber}&nbsp;</h4>
                                        <h4 style={{fontSize: '30px'}}>Duration: {this.state.itemInfo.minDuration} : {this.state.itemInfo.secDuration} </h4>
                                        <h4 style={{fontSize: '30px'}}>Popularity: {this.state.itemInfo.popularity}&nbsp;</h4>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )
        }

        return(<div>Loading</div>);
    }
}

//Component for rendering the album's data
class AlbumInfo extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isReady: false,
            id: props.id
        }

        this.fetchAlbumInfo = this.fetchAlbumInfo.bind(this);

        this.fetchAlbumInfo();
    }

    //Fetching the album's data from the "/album" endpoint
    fetchAlbumInfo(){
        fetch("/album?" + new URLSearchParams({
            albumId: this.state.id
        }))
            .then(response => response.json())
            .then(
                (response) => {
                    console.log(response);

                    this.setState({
                        itemInfo: response,
                        isReady: true,
                    });
                },
                (error) => {
                    alert(error);
                }
            )
    }

    render() {
        if (this.state.isReady){
            return (
                <div className="results">
                   <div className="container">
                        <div className="row" style={{marginTop: '23px', marginBottom: '23px'}}>
                            <div className="col">
                                <div className="card" style={{position: 'relative'}}>
                                    <div className="card-body" style={{textAlign: 'center', display: 'inline-block'}}>
                                        <img src= {this.state.itemInfo.albumImage} style={{width: '450px', borderRadius: '10px'}}/>
                                    </div>
                                </div>
                            </div>
                            <div className="col">
                                <div className="card" style={{height: '484.891px', overflowY: 'scroll'}}>
                                    <div className="card-body" style={{marginLeft: '15px'}}>
                                        <h4 className="card-title" style={{fontSize: '67.376px', marginBottom: '10px'}}>
                                            <a href={this.state.itemInfo.externalLink} target={'_blank'} style={{textDecoration: 'none', color: 'black'}}>
                                                {this.state.itemInfo.albumName}
                                            </a>
                                        </h4>
                                        <h4 style={{textDecoration: 'underline', marginBottom: '23px'}}>Overview:</h4>
                                        <h4 style={{fontSize: '30px'}}>Artist: {this.state.itemInfo.artistName} &nbsp;</h4>
                                        <h4 style={{fontSize: '30px'}}>Type: {this.state.itemInfo.type}</h4>
                                        <h4 style={{fontSize: '30px'}}>Release Date: {this.state.itemInfo.realeaseDate}</h4>
                                        <h4 style={{fontSize: '30px'}}>Label: {this.state.itemInfo.label} &nbsp;</h4>
                                        <h4 style={{fontSize: '30px'}}>Popularity: {this.state.itemInfo.popularity} &nbsp;<br/></h4>
                                        <div>
                                            <h4 style={{fontSize: '30px'}}>Tracks&nbsp;</h4>
                                            <ul>
                                                {
                                                    this.state.itemInfo.tracks.map((t) => {
                                                        return( <li style={{marginLeft: '10px'}}> {t} </li>);
                                                    })
                                                }
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            </div>
                        </div>
                    </div>
            )
        }

        return(<div>Loading</div>);
    }
}

//Component for rendering the artist's data
class ArtistInfo extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isReady: false,
            id: props.id
        }

        this.fetchArtistInfo = this.fetchArtistInfo.bind(this);

        this.fetchArtistInfo();
    }

    //Fetching the artist's data from the "/artist's" endpoint
    fetchArtistInfo(){
        fetch("/artist?" + new URLSearchParams({
            artistId: this.state.id
        }))
            .then(response => response.json())
            .then(
                (response) => {
                    console.log(response);

                    this.setState({
                        itemInfo: response,
                        isReady: true,
                    });
                },
                (error) => {
                    alert(error);
                }
            )
    }

    render() {
        if (this.state.isReady){
            return (
                <div>
                    <div className="container">
                        <div className="row" style={{marginTop: '23px', marginBottom: '23px'}}>
                            <div className="col">
                                <div className="card" style={{position: 'relative'}}>
                                    <div className="card-body" style={{textAlign: 'center', display: 'inline-block'}}>
                                        <img src={this.state.itemInfo.imageLink} style={{width: '450px', borderRadius: '10px'}}/>
                                    </div>
                                </div>
                            </div>
                            <div className="col">
                                <div className="card" style={{height: '484.891px', overflowY: 'scroll'}}>
                                    <div className="card-body" style={{marginLeft: '15px'}}>
                                        <h4 className="card-title" style={{fontSize: '67.376px', marginBottom: '10px'}}>
                                            <a href={this.state.itemInfo.externalLink} target={'_blank'} style={{textDecoration: 'none', color: 'black'}}>
                                                {this.state.itemInfo.artistName}
                                            </a>
                                        </h4>
                                        <h4 style={{textDecoration: 'underline', marginBottom: '23px'}}>Overview:</h4>
                                        <h4 style={{fontSize: '30px'}}>Type: {this.state.itemInfo.type} &nbsp;</h4>
                                        <h4 style={{fontSize: '30px'}}>Followers: {this.state.itemInfo.followers} </h4>
                                        <h4 style={{fontSize: '30px'}}>Popularity: {this.state.itemInfo.popularity} </h4>
                                        <div>
                                            <h4 style={{fontSize: '30px'}}>Genres:</h4>
                                            <ul>
                                                {
                                                    this.state.itemInfo.genres.map((g) => {
                                                        return( <li style={{marginLeft: '10px'}}> {g} </li>);
                                                    })
                                                }
                                            </ul>
                                        </div>
                                        <div>
                                            <h4 style={{fontSize: '30px'}}>Albums</h4>
                                            <ul>
                                                {
                                                    this.state.itemInfo.albumList.map((list) => {
                                                        return( <li style={{marginLeft: '10px'}}> {list} </li>);
                                                    })
                                                }
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            )
        }

        return(<div>Loading</div>);
    }
}


ReactDOM.render(
    <SearchComponent />,
    document.getElementById('search-area')
);

history.scrollRestoration = 'manual';