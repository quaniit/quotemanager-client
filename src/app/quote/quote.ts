export class Quote{
    id: string;
    symbol: string;
    price: number;
    availableVolume: number;
    expiration: string;
    constructor(id: string, symbol: string, price: number, availableVolume: number, expiration: string){
        this.id = id;
        this.symbol = symbol;
        this.price = price;
        this.availableVolume = availableVolume;
        this.expiration = expiration;
    }
}